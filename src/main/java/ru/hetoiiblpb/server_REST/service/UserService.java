package ru.hetoiiblpb.server_REST.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.server_REST.dto.UserDto;
import ru.hetoiiblpb.server_REST.model.User;

import java.util.List;

public interface UserService {

    @Transactional
    User register (User user);

    @Transactional
    List<User> getAll();

    @Transactional
    User findByUsername(String username);

    @Transactional
    User findById(Long id);

    @Transactional
    void delete(Long id);

    @Transactional
    void update(User user);

    @Transactional
    User toUser(UserDto reqUser);
}
