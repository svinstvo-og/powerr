package pj.powerr.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.powerr.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, String>{
    Exercise findByUsername(String name);
}
