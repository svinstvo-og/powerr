package pj.powerr.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.powerr.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelegramId(String telegramId);
}
