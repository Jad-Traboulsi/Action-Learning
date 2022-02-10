package fr.epita.actionlearning;

import fr.epita.actionlearning.entities.Attendance;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.services.impl.AttendanceService;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.InstructorService;
import fr.epita.actionlearning.services.impl.StudentService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class AttendanceTest {
    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);

    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;
    @Autowired
    AttendanceService attendanceService;

    /*@Test
    public void addStudentsToCourse() throws AlreadyExistingException {
        String name="ActionLearning";

        Set<Attendance> attendance2 = new HashSet<>();
        for(Course c:courseService.search(name))
        {
            attendance2.add(new Attendance(studentService.search("04:20:35:C2:A8:6A:80"),
                    c,
                    Time.valueOf("00:00:00"),Time.valueOf("00:00:00")));
        }

        for (Attendance a: attendance2) {
            attendanceService.add(a);
        }
    }*/


    @Test
    public void getAll()
    {
        for (Attendance a: attendanceService.getAll()) {
            logger.info(a.toString());
        }
    }
}
