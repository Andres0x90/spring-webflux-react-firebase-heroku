package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
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

class GetUseCaseTest {
    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
    }
    @Test
    public void getUserUseCaseTest()
    {
        var answer = getAnswerData();
        var question = getQuestionData();
        when(questionRepository.findById("qqqq")).thenReturn(Mono.just(question).map(mapperUtils.mapperToQuestion(question.getId())));
        when(answerRepository.findAllByQuestionId("qqqq")).thenReturn(Flux.just(answer).map(mapperUtils.mapperToAnswer()));

        var getQuestion = new GetUseCase(mapperUtils, questionRepository, answerRepository);

        StepVerifier.create(getQuestion.apply("qqqq"))
                .expectNextMatches(questionDTO ->
                {
                    assert question.getId().equalsIgnoreCase(questionDTO.getId());
                    assert question.getQuestion().equalsIgnoreCase(questionDTO.getQuestion());
                    assert question.getType().equalsIgnoreCase(questionDTO.getType());
                    assert question.getCategory().equalsIgnoreCase(questionDTO.getCategory());
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
    QuestionDTO getQuestionData()
    {
        var question = new QuestionDTO();
        question.setId("qqqq");
        question.setType("type1");
        question.setCategory("category1");
        question.setQuestion("¿What is science?");
        return question;
    }

}