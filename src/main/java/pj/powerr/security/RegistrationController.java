package pj.powerr.security;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.User;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "auth/signup", consumes = "application/json")
    public User createUser(@RequestBody final User user) throws EntityExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EntityExistsException();
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }
}