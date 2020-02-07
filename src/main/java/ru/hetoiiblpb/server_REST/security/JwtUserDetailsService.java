package ru.hetoiiblpb.server_REST.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hetoiiblpb.server_REST.model.User;
import ru.hetoiiblpb.server_REST.security.jwt.JwtUser;
import ru.hetoiiblpb.server_REST.security.jwt.JwtUserFactory;
import ru.hetoiiblpb.server_REST.service.UserService;


@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not fond!");
            }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username \"{}\" was successfully loaded", username);

        return jwtUser;
    }
}
