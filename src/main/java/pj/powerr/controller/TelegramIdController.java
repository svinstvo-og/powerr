package pj.powerr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.User;

import java.util.Optional;

@Controller
public class TelegramIdController {

    @GetMapping("/telegram-link")
    public String telegramLink() {
        return "telegram-link";
    }

    @Autowired
    private UserRepository userRepository;

    public  Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // Cast to your custom UserDetails implementation
                UserDetails userDetails = (UserDetails) principal;
                return ((User) userDetails).getId(); // Assuming `User` includes the ID
            }
        }
        throw new IllegalStateException("User is not logged in");
    }

    @PostMapping(value = "/telegram-link", consumes = "application/json")
    public User telegramLinkPost(@RequestBody final User requestUser) {
        if (userRepository.findByUsername(requestUser.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("Username " + requestUser.getUsername() + " does not exist");
        };
        User user = userRepository.findByUsername(requestUser.getUsername()).get();
        user.setTelegramId(requestUser.getTelegramId());
        return userRepository.save(user);
    }
}
