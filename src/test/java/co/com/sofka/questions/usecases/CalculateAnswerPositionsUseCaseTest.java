package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
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

class CalculateAnswerPositionsUseCaseTest {
    AnswerRepository answerRepository;
    MapperUtils mapperUtils;
    GetAnswerUseCase getAnswerUseCase;
    UpdateAnswerUseCase updateAnswerUseCase;
    CalculateAnswerPositionsUseCase calculateAnswerPositionsUseCase;
    GetUseCase getUseCase;
    QuestionRepository questionRepository;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        getAnswerUseCase = new GetAnswerUseCase(mapperUtils, answerRepository);
        updateAnswerUseCase = new UpdateAnswerUseCase(mapperUtils, answerRepository, getAnswerUseCase);
        getUseCase = new GetUseCase(mapperUtils,questionRepository, answerRepository);
        calculateAnswerPositionsUseCase = new CalculateAnswerPositionsUseCase(getUseCase, updateAnswerUseCase);
    }

    @Test
    public void calculatePositionAnswer()
    {
        var answer = getAnswerData();
        var question = getQuestionData();
        when(answerRepository.findById("xxxx")).thenReturn(Mono.just(answer).map(mapperUtils.mapperToAnswer()));
        when(answerRepository.save(Mockito.any())).thenReturn(Mono.just(answer).map(mapperUtils.mapperToAnswer()));
        when(questionRepository.findById("qqqq")).thenReturn(Mono.just(question).map(mapperUtils.mapperToQuestion(question.getId())));
        when(answerRepository.findAllByQuestionId("qqqq")).thenReturn(Flux.just(answer).map(mapperUtils.mapperToAnswer()));

        StepVerifier.create(calculateAnswerPositionsUseCase.apply("qqqq"))
                .verifyComplete();
    }

    AnswerDTO getAnswerData()
    {
        var answer = new AnswerDTO("qqqq", "uuuu", "answer1");
        answer.setId("xxxx");
        answer.setUpVotes(new ArrayList<>());
        answer.setDownVotes(new ArrayList<>());
        return answer;
    }
    QuestionDTO getQuestionData()
    {
        var question = new QuestionDTO();
        question.setId("qqqq");

        return question;
    }
}