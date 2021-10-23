package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OwnerListUseCaseTest {
    QuestionRepository questionRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
    }

    @Test
    public void getQuestionsByUser()
    {
        var questions = getQuestionsData();
        when(questionRepository.findByUserId("uuuu")).thenReturn(Flux.fromIterable(questions)
                .map(questionDTO ->
                {var mapped= mapperUtils.mapperToQuestion(questionDTO.getId());
                    return mapped.apply(questionDTO);
                }));

        var getQuestionsByUserId = new OwnerListUseCase(mapperUtils, questionRepository);

        StepVerifier.create(getQuestionsByUserId.apply("uuuu"))
                .expectNextMatches(questionDTO ->
                {
                    assert questions.get(0).getId().equalsIgnoreCase(questionDTO.getId());
                    assert questions.get(0).getQuestion().equalsIgnoreCase(questionDTO.getQuestion());
                    assert questions.get(0).getType().equalsIgnoreCase(questionDTO.getType());
                    assert questions.get(0).getCategory().equalsIgnoreCase(questionDTO.getCategory());
                    return true;
                })
                .expectNextMatches(questionDTO ->
                {
                    assert questions.get(1).getId().equalsIgnoreCase(questionDTO.getId());
                    assert questions.get(1).getQuestion().equalsIgnoreCase(questionDTO.getQuestion());
                    assert questions.get(1).getType().equalsIgnoreCase(questionDTO.getType());
                    assert questions.get(1).getCategory().equalsIgnoreCase(questionDTO.getCategory());
                    return true;
                }).verifyComplete();
    }

    List<QuestionDTO> getQuestionsData()
    {
        var question1 = new QuestionDTO();
        question1.setId("xxxx");
        question1.setType("type1");
        question1.setCategory("category1");
        question1.setUserId("uuuu");
        question1.setQuestion("question1");
        var question2 = new QuestionDTO();
        question2.setId("yyyy");
        question2.setType("type2");
        question2.setCategory("category2");
        question2.setUserId("uuuu");
        question2.setQuestion("question2");

        var questions = new ArrayList<QuestionDTO>();
        questions.add(question1);
        questions.add(question2);
        return questions;
    }
}