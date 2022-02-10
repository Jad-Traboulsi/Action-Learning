package fr.epita.actionlearning.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name="id")
    private String id;

    @OneToOne
    @JoinColumn(name = "contact", nullable = false)
    private Contact contact;

    @Column(name="elevator_access")
    private boolean elevatorAccess;

    public Student() {
    }

    public Student(String id, Contact contact, boolean elevatorAccess) {
        this.id = id;
        this.contact = contact;
        this.elevatorAccess = elevatorAccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    public boolean isElevatorAccess() {
        return elevatorAccess;
    }

    public void setElevatorAccess(boolean elevatorAccess) {
        this.elevatorAccess = elevatorAccess;
    }

    @Override
    public String toString() {

        return "Student{" +
                "id='" + id + '\'' +
                ", contact=" + contact +
                ", elevatorAccess=" + elevatorAccess +
                '}';
    }
}
