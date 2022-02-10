package fr.epita.actionlearning.entities;

import javax.persistence.*;

@Entity
@Table(name="admin")
public class Administration {

    @Id
    @Column(name="id")
    private String id;

    @OneToOne
    @JoinColumn(name = "contact", nullable = false)
    private Contact contact;

    @Column(name="elevator_access")
    private boolean elevatorAccess;

    public Administration() {
    }

    public Administration(String id, Contact contact, boolean elevatorAccess) {
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
        return "Administration{" +
                "id='" + id + '\'' +
                ", contact=" + contact +
                ", elevatorAccess=" + elevatorAccess +
                '}';
    }
}
