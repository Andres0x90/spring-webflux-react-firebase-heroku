package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

class UpdateUseCaseTest {
    QuestionRepository questionRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
    }
    @Test
    public void updateQuestionTest()
    {
        var updateQuestion = new UpdateUseCase(mapperUtils, questionRepository);
        var question = getQuestionData();
        when(questionRepository.save(Mockito.any()))
                .thenReturn(Mono.just(question).map(mapperUtils.mapperToQuestion(question.getId())));

        StepVerifier.create(updateQuestion.apply(question)).expectNextMatches(
                id ->
                {
                    return question.getId().equalsIgnoreCase(id);
                }
        ).verifyComplete();
    }
    QuestionDTO getQuestionData()
    {
        var question = new QuestionDTO();
        question.setId("xxxx");
        question.setType("type1");
        question.setCategory("category1");
        return question;
    }
}