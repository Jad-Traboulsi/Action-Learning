package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Attendance;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    @Query("SELECT a from Attendance a WHERE a.student = ?1 AND " +
            "a.course=?2 AND a.entranceAttendance=?3 AND a.quitAttendance = ?4")
    List<Attendance> searchAttendance(Student student, Course course, Time entrance, Time quit);

    @Query("SELECT s FROM Attendance s WHERE s.student=?1")
    List<Attendance> searchStudentCourses(Student student);

    @Transactional
    @Modifying
    @Query("UPDATE Attendance a SET a.entranceAttendance = ?1 WHERE a.student = ?2 AND a.course = ?3")
    void updateEntrance(Time entrance,Student student,Course course);

    @Transactional
    @Modifying
    @Query("UPDATE Attendance a SET a.quitAttendance = ?1 WHERE a.student = ?2 AND a.course = ?3")
    void updateQuit(Time quit,Student student,Course course);

}