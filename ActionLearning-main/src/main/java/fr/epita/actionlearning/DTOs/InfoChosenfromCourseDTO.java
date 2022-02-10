package fr.epita.actionlearning.DTOs;

import fr.epita.actionlearning.entities.Instructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

public class InfoChosenfromCourseDTO {

    private long id;

    private String courseName;

    private Time startTime;

    private Time endTime;

    public InfoChosenfromCourseDTO() {
    }

    public InfoChosenfromCourseDTO(long id, String courseName, Time startTime, Time endTime) {
        this.id = id;
        this.courseName = courseName;
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
        return "InfoChosenfromCourseDTO{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
