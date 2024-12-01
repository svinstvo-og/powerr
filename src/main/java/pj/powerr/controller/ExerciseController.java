package pj.powerr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pj.powerr.db.ExerciseRepository;
import pj.powerr.db.UserRepository;
import pj.powerr.entity.Exercise;
import pj.powerr.entity.User;

import java.util.List;

@RestController
@RequestMapping("api/exercise")
public class ExerciseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @PostMapping
    public Exercise addExercise(@RequestBody Exercise exercise) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        exercise.setUser(user);
        return exerciseRepository.save(exercise);
    }

    @GetMapping
    public List<Exercise> getExercises() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    
        return exerciseRepository.findByUsername(user.getUsername());
    }
}
