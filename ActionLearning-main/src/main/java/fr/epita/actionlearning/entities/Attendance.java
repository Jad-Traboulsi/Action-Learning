package fr.epita.actionlearning.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name="Attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course")
    private Course course;

    @Column(name = "entranceAttendance")
    private Time entranceAttendance;

    @Column(name = "quitAttendance")
    private Time quitAttendance;

    public Attendance() {

    }

    public Attendance(Student student, Course course, Time entranceAttendance, Time quitAttendance) {

        this.student = student;
        this.course = course;
        this.entranceAttendance = entranceAttendance;
        this.quitAttendance = quitAttendance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourses(Course course) {
        this.course = course;
    }

    public Time getEntranceAttendance() {
        return entranceAttendance;
    }

    public void setEntranceAttendance(Time entranceAttendance) {
        this.entranceAttendance = entranceAttendance;
    }

    public Time getQuitAttendance() {
        return quitAttendance;
    }

    public void setQuitAttendance(Time quitAttendance) {
        this.quitAttendance = quitAttendance;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", entranceAttendance=" + entranceAttendance +
                ", quitAttendance=" + quitAttendance +
                '}';
    }
}

