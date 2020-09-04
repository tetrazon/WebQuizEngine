package engine.security;

import engine.entity.LoginForm;
import engine.entity.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        engine.entity.User foundedUser = userRepository.findByUsername(email);

        if (foundedUser == null) {
            throw new UsernameNotFoundException(
                    "User with email '" + email + "' is not found");
        }

        return foundedUser;
    }

    public ResponseEntity addUser(LoginForm loginForm) {
        //System.out.println(passwordEncoder.encode(loginForm.getPassword()));
        User userToCheck = userRepository.findByUsername(loginForm.getEmail());
        if (userToCheck != null) {
            //System.out.println(userToCheck.getUsername());
            //System.out.println(userToCheck.getPassword());
            return ResponseEntity.badRequest().body("User with email '"
                    + loginForm.getEmail() + "' is already existed!");
        }
        User userToSave = new User();

        userToSave.setPassword(passwordEncoder.encode(loginForm.getPassword()));
        userToSave.setUsername(loginForm.getEmail());
        userRepository.save(userToSave);
        return ResponseEntity.ok().body(userRepository.save(userToSave));
    }
}
