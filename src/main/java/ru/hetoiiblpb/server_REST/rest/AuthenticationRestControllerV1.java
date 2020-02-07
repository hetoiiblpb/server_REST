package ru.hetoiiblpb.server_REST.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hetoiiblpb.server_REST.dto.AuthenticationRequestDTO;
import ru.hetoiiblpb.server_REST.model.User;
import ru.hetoiiblpb.server_REST.security.jwt.JwtTokenProvider;
import ru.hetoiiblpb.server_REST.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/restapi/v1/auth/")
public class AuthenticationRestControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private  final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login (@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            String username = requestDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,requestDTO.getPassword()));
            User user = userService.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User with username: " + username + " not found");
                }
            String token = jwtTokenProvider.createToken(username,user.getRoles());
            Map<Object,Object> response = new HashMap<>();
            response.put("username",username);
            response.put("token",token);
            response.put("roles",user.getRoles()
                    .stream()
                    .map(role -> role.getName())
                    .collect(Collectors.joining(",","[","]")));

        return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
