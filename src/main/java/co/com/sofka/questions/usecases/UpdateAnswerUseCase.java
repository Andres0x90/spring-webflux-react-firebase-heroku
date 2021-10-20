package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class UpdateAnswerUseCase implements Function<AnswerDTO,Mono<AnswerDTO>> {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final GetAnswerUseCase getAnswerUseCase;

    public UpdateAnswerUseCase(MapperUtils mapperUtils, AnswerRepository answerRepository
    ,GetAnswerUseCase getAnswerUseCase) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
        this.getAnswerUseCase = getAnswerUseCase;
    }
    @Override
    public Mono<AnswerDTO> apply(AnswerDTO answerDTO) {
        return getAnswerUseCase.apply(answerDTO.getId())
                .flatMap((originalAnswerDTO)->
                        answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO)))
                .map(mapperUtils.mapEntityToAnswer());
    }
}
