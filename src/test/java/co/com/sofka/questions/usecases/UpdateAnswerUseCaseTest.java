package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

class UpdateAnswerUseCaseTest {
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
    }
    @Test
    public void updateUserUseCaseTest()
    {
        var answer = getAnswerData();
        when(answerRepository.findById("xxxx")).thenReturn(Mono.just(answer).map(mapperUtils.mapperToAnswer()));
        when(answerRepository.save(Mockito.any())).thenReturn(Mono.just(answer).map(mapperUtils.mapperToAnswer()));
        GetAnswerUseCase getAnswerUseCase = new GetAnswerUseCase(mapperUtils, answerRepository);
        var updateAnswer = new UpdateAnswerUseCase(mapperUtils,answerRepository,getAnswerUseCase);

        StepVerifier.create(updateAnswer.apply(answer))
                .expectNextMatches(answerDTO ->
                {
                    answer.getQuestionId().equalsIgnoreCase(answerDTO.getQuestionId());
                    answer.getUserId().equalsIgnoreCase(answerDTO.getUserId());
                    answer.getAnswer().equalsIgnoreCase(answerDTO.getAnswer());
                    return true;
                }).verifyComplete();
    }
    AnswerDTO getAnswerData()
    {
        var answer = new AnswerDTO("qqqq", "uuuu", "answer1");
        answer.setId("xxxx");
        answer.setQuestionId("qqqq");
        answer.setUpVotes(new ArrayList<>());
        answer.setDownVotes(new ArrayList<>());
        return answer;
    }
}