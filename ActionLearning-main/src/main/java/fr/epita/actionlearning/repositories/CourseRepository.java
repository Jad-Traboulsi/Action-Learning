package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Address;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("SELECT a from Course a WHERE lower(a.courseName) = lower(?1)")
    List<Course> searchByName(String courseName);
    @Query("SELECT a from Course a join a.instructors i where i = ?1")
    List<Course> searchByInstructor(Instructor instructor);
    @Query("select a from Course a where lower(a.courseName) = lower(?1) and a.startTime = ?2 and a.endTime = ?3")
    List<Course> searchByNameAndTime(String courseName, Time startTime,Time endTime);
}
