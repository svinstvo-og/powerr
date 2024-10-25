package pj.powerr.entity;

import jakarta.persistence.*;

@Entity
public class Excercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String muscleGroup;

    public Long getId(Excercise excercise) {
        return excercise.id;
    }

    public String getName(Excercise excercise) {
        return excercise.name;
    }

    public String getMuscleGroup(Excercise excercise) {
        return excercise.muscleGroup;
    }

    public Void setName(Excercise excercise, String name) {
        this.name = name;
        return null;
    }

    public Void setMuscleGroup(Excercise excercise, String muscleGroup) {
        this.muscleGroup = muscleGroup;
        return null;
    }

    public Excercise () {}

    public Excercise(String name, String muscleGroup) {
        this.name = name;
        this.muscleGroup = muscleGroup;
    }
}
