package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

class GetAnswerUseCaseTest {
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
    }

    @Test
    public void getAnswer()
    {
        var getAnswer = new GetAnswerUseCase(mapperUtils, answerRepository);
        var answer = getAnswerData();
        when(answerRepository.findById("xxxx")).thenReturn(Mono.just(answer).map(mapperUtils.mapperToAnswer()));

        StepVerifier.create(getAnswer.apply("xxxx")).expectNextMatches(
                answerDTO ->
                {
                    assert answer.getId().equalsIgnoreCase(answerDTO.getId());
                    assert answer.getAnswer().equalsIgnoreCase(answerDTO.getAnswer());
                    return true;
                }
        ).verifyComplete();
    }
    AnswerDTO getAnswerData()
    {
        var answer = new AnswerDTO("qqqq", "uuuu", "answer1");
        answer.setId("xxxx");
        answer.setAnswer("idk");
        answer.setUpVotes(new ArrayList<>());
        answer.setDownVotes(new ArrayList<>());
        return answer;
    }
}