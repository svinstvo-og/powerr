package pj.powerr.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String telegramId;

    Long getId(User user) {
        return user.id;
    }

    String getUsername(User user) {
        return user.username;
    }

    String getTelegramId(User user) {
        return user.telegramId;
    }
}
