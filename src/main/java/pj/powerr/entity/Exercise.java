package pj.powerr.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exercise")
@Setter
@Getter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exId;

    @Column(nullable = false)
    private String name;
    private int reps;
    private double weight;

    //@Column(nullable = false)
    private LocalDateTime created;

    @ManyToOne
    private User user;
}