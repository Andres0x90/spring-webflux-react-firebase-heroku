package co.com.sofka.questions.usecases;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VoteDownUseCase {
    private final GetAnswerUseCase getAnswerUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;
    private final MapperUtils mapperUtils;

    public VoteDownUseCase(GetAnswerUseCase getAnswerUseCase, UpdateAnswerUseCase updateAnswerUseCase,
                           MapperUtils mapperUtils) {
        this.getAnswerUseCase = getAnswerUseCase;
        this.updateAnswerUseCase = updateAnswerUseCase;
        this.mapperUtils = mapperUtils;
    }

    public Mono<Void> apply(String answerId, String userId) {
        return getAnswerUseCase.apply(answerId)
                .map(mapperUtils.mapperToAnswer())
                .map(answer ->
                {
                    answer.removeUpVote(userId);
                    answer.addDownVote(userId);
                    return answer;
                })
                .map(mapperUtils.mapEntityToAnswer())
                .flatMap(updateAnswerUseCase::apply).then();
    }
}
