package ru.practicum.shareitgateway.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareitgateway.client.BaseClient;
import ru.practicum.shareitgateway.user.dto.UserDto;


@Service
@Slf4j
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> create(UserDto userRequestDto) {
        log.info("Create user  {}", userRequestDto);
        return post("", userRequestDto);
    }

    public ResponseEntity<Object> getUserById(Long userId) {
        log.info("Get user with id = {}", userId);
        return get("/" + userId);
    }

    public ResponseEntity<Object> updateUser(Long userId, UserDto userRequestDto) {
        log.info("Update user with id = {}", userId);
        return patch("/" + userId, userRequestDto);
    }

    public ResponseEntity<Object> findAll() {
        log.info("Get all users");
        return get("");
    }

    public ResponseEntity<Object> deleteUserById(Long userId) {
        log.info("Remove user with id = {}", userId);
        return delete("/" + userId);
    }
}