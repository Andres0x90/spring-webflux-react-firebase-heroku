package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.services.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final GetUseCase getUseCase;
    private final EmailService emailService;

    public AddAnswerUseCase(MapperUtils mapperUtils, GetUseCase getUseCase,
                            AnswerRepository answerRepository, EmailService emailService) {
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.mapperUtils = mapperUtils;
        this.emailService = emailService;
    }

    public Mono<QuestionDTO> apply(AnswerDTO answerDTO){
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the answer is required");
        return getUseCase.apply(answerDTO.getQuestionId()).flatMap(question ->
                answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO))
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);
                            emailService.sendmail(question.getEmail(), question.getQuestion());
                            return question;
                        })
        );
    }

}
