package pj.powerr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.User;

@Controller
public class TelegramIdController {

    @GetMapping("/telegram-link")
    public String telegramLink() {
        return "telegram-link";
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/telegram-link", consumes = "application/json")
    public User telegramLinkPost(@RequestBody final User user) {
        return userRepository.save(user);
    }
}
