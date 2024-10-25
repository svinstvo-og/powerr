package pj.powerr.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.powerr.entity.Excercise;

public interface ExerciseRepository extends JpaRepository<Excercise, Long>{
    Excercise findByName(String name);
}
