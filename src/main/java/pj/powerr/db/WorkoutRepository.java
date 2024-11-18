package pj.powerr.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.powerr.entity.User;
import pj.powerr.entity.Workout;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUser(User user);
}
