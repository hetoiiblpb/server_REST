package ru.hetoiiblpb.server_REST.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hetoiiblpb.server_REST.model.Role;
import ru.hetoiiblpb.server_REST.model.Status;
import ru.hetoiiblpb.server_REST.model.User;
import ru.hetoiiblpb.server_REST.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private static RoleRepository roleRepository;
    private Long id;
    private  String username;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;
    private  String roles;


    public UserDto (@NotNull User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.joining(",","[","]"));
    }


//    public User toUser () {
//        User user = new User();
//        user.setId(id);
//        user.setUsername(username);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setRoles(Arrays.stream(roles.split("[:punct:]")).);
//        return user;
//    }

}
