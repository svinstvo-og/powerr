package pj.powerr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pj.powerr.auth.JwtUtil;
import pj.powerr.auth.LoginRequest;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.User;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";  // Redirect authenticated users to the home page
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );

            // Set the authentication in the Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optional: Generate the JWT token if needed
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            model.addAttribute("token", token); // Optional: Pass token to the view if needed

            // Redirect to the user’s home page or dashboard view
            return "redirect:/home";  // Adjust to the correct URL for your home page

        } catch (Exception e) {
            // On authentication failure, show an error message
            model.addAttribute("loginError", "Invalid username or password. Please try again.");
            return "login";  // Return to the login page with an error message
        }
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, BindingResult result, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "error.user", "Username is already taken.");
        }

        if (result.hasErrors()) {
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);  // Save the new user to the database

        // Redirect to login page after successful registration
        return "redirect:/auth/login";
    }
}
