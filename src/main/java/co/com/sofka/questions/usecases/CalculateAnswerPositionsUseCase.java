package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class CalculateAnswerPositionsUseCase implements Function<String, Mono<Void>> {
    private final GetUseCase getUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;


    public CalculateAnswerPositionsUseCase(GetUseCase getUseCase,
                                           UpdateAnswerUseCase updateAnswerUseCase ) {
        this.getUseCase = getUseCase;
        this.updateAnswerUseCase = updateAnswerUseCase;
    }

    Flux<Integer> getPositions(String questionId)
    {
        return  getUseCase.apply(questionId).map(QuestionDTO::getAnswers)
                .map(answerDTOS -> answerDTOS.stream().map(answerDTO ->
                        answerDTO.getUpVotes().size() - answerDTO.getDownVotes().size()
                ))
                .map( scores ->
                {
                    var originalScores = scores.collect(Collectors.toList());
                    var scoresSorted = originalScores.stream().sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList());
                    return originalScores.stream().map(scoresSorted::indexOf);
                }).flatMapMany(Flux::fromStream);
    }

    @Override
    public Mono<Void> apply(String questionId) {

         return getUseCase.apply(questionId).map(QuestionDTO::getAnswers)
                .flatMapMany(Flux::fromIterable)
                 .zipWith(getPositions(questionId))
                 .map(dtoIntegerTuple ->
                {
                    var answerDTO = dtoIntegerTuple.getT1();
                    var position = dtoIntegerTuple.getT2();
                    answerDTO.setPosition(position);
                    return answerDTO;
                }).map(answerDTO ->
                 {
                     updateAnswerUseCase.apply(answerDTO).subscribe();
                     return answerDTO;
                 }).then();

    }
}
