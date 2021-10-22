package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateUserUseCaseTest {
    UserRepository userRepository;
    MapperUtils mapperUtils;

    @BeforeEach
    public void setup() {
        mapperUtils = new MapperUtils();
        userRepository = mock(UserRepository.class);
    }
    @Test
    public void updateUserUseCaseTest()
    {
        var updateUserUseCase = new UpdateUserUseCase(mapperUtils, userRepository);
        var user = getUserData();
        when(userRepository.save(Mockito.any())).thenReturn(Mono.just(user).map(mapperUtils.mapperToUser()));

        StepVerifier.create(updateUserUseCase.apply(user)).expectNextMatches(
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