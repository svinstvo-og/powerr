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

    public Long getId(User user) {
        return user.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername(User user) {
        return user.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelegramId(User user) {
        return user.telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getPassword(User user) {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
