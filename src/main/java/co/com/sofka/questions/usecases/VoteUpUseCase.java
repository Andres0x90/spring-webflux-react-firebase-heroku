package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VoteUpUseCase {
    private final GetAnswerUseCase getAnswerUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;
    private final CalculateAnswerPositionsUseCase calculateAnswerPositionsUseCase;
    private final MapperUtils mapperUtils;

    public VoteUpUseCase(GetAnswerUseCase getAnswerUseCase, UpdateAnswerUseCase updateAnswerUseCase
    , CalculateAnswerPositionsUseCase calculateAnswerPositionsUseCase, MapperUtils mapperUtils) {
        this.getAnswerUseCase = getAnswerUseCase;
        this.updateAnswerUseCase = updateAnswerUseCase;
        this.calculateAnswerPositionsUseCase = calculateAnswerPositionsUseCase;
        this.mapperUtils = mapperUtils;
    }

    public Mono<Void> apply(String answerId, String userId) {
        return getAnswerUseCase.apply(answerId)
                .map(mapperUtils.mapperToAnswer())
                .map(answer ->
                {
                    answer.addUpVote(userId);
                    answer.removeDownVote(userId);
                    return answer;
                })
                .map(mapperUtils.mapEntityToAnswer())
                .flatMap(updateAnswerUseCase)
                .map(AnswerDTO::getQuestionId)
                .flatMap(calculateAnswerPositionsUseCase);
    }
}
