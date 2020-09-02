package engine.controller;

import engine.entity.LoginForm;
import engine.entity.User;
import engine.repository.UserRepository;
import engine.security.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepositoryUserDetailsService userRepositoryUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity addUser( @Valid @RequestBody LoginForm loginForm)  {
            return userRepositoryUserDetailsService.addUser(loginForm);
    }

    //@Secured("USER")
    @GetMapping("/all")
    public ResponseEntity retrievePrincipal() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
