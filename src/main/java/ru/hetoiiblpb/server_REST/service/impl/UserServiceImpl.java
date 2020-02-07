package ru.hetoiiblpb.server_REST.service.impl;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hetoiiblpb.server_REST.dto.UserDto;
import ru.hetoiiblpb.server_REST.model.Role;
import ru.hetoiiblpb.server_REST.model.Status;
import ru.hetoiiblpb.server_REST.model.User;
import ru.hetoiiblpb.server_REST.repository.RoleRepository;
import ru.hetoiiblpb.server_REST.repository.UserRepository;
import ru.hetoiiblpb.server_REST.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User toUser(@NotNull UserDto userDto) {
        User user = new User();
        List<Role> roleList = new ArrayList<>();
        for(String role: userDto.getRoles().replaceAll("\\[", "").replaceAll("]", "")
                .split(",")) {
            roleList.add(roleRepository.findByName(role));
        }
        if (userDto.getId() != null) { user.setId(userDto.getId());}
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        if (!userDto.getPassword().isEmpty()) { user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword())); }
        user.setRoles(roleList);
        user.setStatus(Status.ACTIVE);
        user.setUpdated(new Date());
        return user;
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found",result.size());
        return  result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user:{} found by username \"{}\"",
                new UserDto(result),
                username);
        return  result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
            if (result == null) {
                log.warn("IN findById - no users found by Id: {}", id);
                return null;
            }
        log.info("IN findById - user \"{}\" found by id {}", result.getUsername(), id);
        return  result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} was successfully deleted", id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
        log.info("IN update - user {} was successfully updated", user);
    }
}
