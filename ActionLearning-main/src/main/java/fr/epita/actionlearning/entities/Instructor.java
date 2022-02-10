package fr.epita.actionlearning.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="instructor")
public class Instructor {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="password")
    private String pw;

    @OneToOne
    @JoinColumn(name = "contact", nullable = false)
    private Contact contact;

    @Column(name="elevator_access")
    private boolean elevatorAccess;


    public Instructor() {
    }

    public Instructor(String id, String pw, Contact contact, boolean elevatorAccess) {
        this.id = id;
        this.pw = pw;
        this.contact = contact;
        this.elevatorAccess = elevatorAccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
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
        return "Instructor{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", contact=" + contact +
                ", elevatorAccess=" + elevatorAccess +
                '}';
    }

}
