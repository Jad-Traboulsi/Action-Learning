package fr.epita.actionlearning.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="course")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="courseName")
    private String courseName;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_instructors",
            joinColumns = @JoinColumn(name = "instructor"),
            inverseJoinColumns = @JoinColumn(name = "student"))
    private Set<Instructor> instructors;



    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;

    public Course() {
    }

    public Course(String courseName, Set<Instructor> instructors, Time startTime, Time endTime) {
        this.courseName = courseName;
        this.instructors = instructors;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructors=" + instructors +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
