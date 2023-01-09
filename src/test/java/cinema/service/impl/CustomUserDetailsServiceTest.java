package cinema.service.impl;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class CustomUserDetailsServiceTest {
    private UserDetailsService userDetailsService;
    private UserService userService;
    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        userDetailsService = new CustomUserDetailsService(userService);
    }

    @Test
    void loadUserByUserName_ok() {
        String email = "bob@i.ua";
        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        User user = new User();
        user.setEmail(email);
        user.setPassword("1234");
        user.setRoles(Set.of(role));

        Mockito.when(userService.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails actual = userDetailsService.loadUserByUsername(email);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(email, actual.getUsername());
        Assertions.assertEquals("1234", actual.getPassword());
    }
}