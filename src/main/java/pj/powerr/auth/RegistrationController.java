package pj.powerr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.User;
import pj.powerr.service.UserService;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "auth/signup", consumes = "aaplication/json")
    public User createUser(@RequestBody final User user) {
        return userRepository.save(user);
    }
}