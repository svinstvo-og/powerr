package pj.powerr.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.powerr.entity.Exercise;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, String>{
    List<Exercise> findByUserId(Long userId);
}
