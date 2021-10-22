package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class GetUserUseCaseTest {
    UserRepository userRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        userRepository = mock(UserRepository.class);
    }
    @Test
    public void getUserUseCaseTest()
    {
        var getUserUseCase = new GetUserUseCase(mapperUtils, userRepository);
        var user = getUserData();
        when(userRepository.findById("xxxx")).thenReturn(Mono.just(user).map(mapperUtils.mapperToUser()));

        StepVerifier.create(getUserUseCase.apply("xxxx")).expectNextMatches(
                userDTO ->
                {
                    assert user.getId().equalsIgnoreCase(userDTO.getId());
                    assert user.getName().equalsIgnoreCase(userDTO.getName());
                    assert user.getLastName().equalsIgnoreCase(userDTO.getLastName());
                    assert user.getEmail().equalsIgnoreCase(userDTO.getEmail());
                    return true;
                }
        ).verifyComplete();
    }
    UserDTO getUserData()
    {
        var user = new UserDTO();
        user.setId("xxxx");
        user.setName("Juan");
        user.setLastName("Perez");
        user.setEmail("juanperez@gmail.com");
        return user;
    }
}