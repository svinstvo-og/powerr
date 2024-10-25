package pj.powerr.entity;

import jakarta.persistence.*;

import java.util.Date;

public class Workout {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Excercise exercise;

    private int weight;
    private int reps;
    public Date date;

    public Workout() {}

    public Workout(User user, Excercise exercise, int weight, int reps, Date date) {
        this.user = user;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Excercise getExercise() {
        return exercise;
    }

    public void setExercise(Excercise exercise) {
        this.exercise = exercise;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
