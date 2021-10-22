package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.User;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.UserDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class UpdateUserUseCase implements Function<UserDTO, Mono<UserDTO>> {
    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;
    private final GetUserUseCase getUserUseCase;

    public UpdateUserUseCase(MapperUtils mapperUtils, UserRepository userRepository
    ,GetUserUseCase getUserUseCase) {
        this.userRepository = userRepository;
        this.mapperUtils = mapperUtils;
        this.getUserUseCase = getUserUseCase;
    }
    @Override
    public Mono<UserDTO> apply(UserDTO userDTO) {
        return userRepository.save(mapperUtils.mapperToUser().apply(userDTO))
                .map(mapperUtils.mapEntityToUser());
    }
}