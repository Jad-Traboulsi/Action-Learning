package fr.epita.actionlearning.DTOs;

import fr.epita.actionlearning.entities.Course;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructorDTO {
    private Boolean isInstructor;
    private List<InfoChosenfromCourseDTO> courses;
    private String Password;
    private String UID;

    public InstructorDTO() {
    }

    public InstructorDTO(Boolean isInstructor, List<InfoChosenfromCourseDTO> courses, String password, String UID) {
        this.isInstructor = isInstructor;
        this.courses = courses;
        Password = password;
        this.UID = UID;
    }

    public Boolean getIsInstructor() {
        return isInstructor;
    }

    public void setIsInstructor(Boolean instructor) {
        isInstructor = instructor;
    }

    public List<InfoChosenfromCourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<InfoChosenfromCourseDTO> courses) {
        this.courses = courses;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Override
    public String toString() {
        return "InstructorDTO{" +
                "isInstructor=" + isInstructor +
                ", courses=" + courses.stream().map(InfoChosenfromCourseDTO::getCourseName).collect(Collectors.toList()) +
                ", Password='" + Password + '\'' +
                ", UID='" + UID + '\'' +
                '}';
    }
}
