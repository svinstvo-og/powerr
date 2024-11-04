package pj.powerr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pj.powerr.auth.JwtUtil;
import pj.powerr.auth.LoginRequest;

//@RestController
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String loginPage(Model model) {
        // Add any attributes needed for the view
        return "login"; // This corresponds to "login.html" in the templates folder
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        return jwtUtil.generateToken(loginRequest.getUsername());
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";
    }


    @PostMapping("/signup")
    public String signup() {
        //TODO
        return "signup";
    }
}
