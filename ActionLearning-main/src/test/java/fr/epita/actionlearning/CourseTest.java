package fr.epita.actionlearning;

import fr.epita.actionlearning.entities.Attendance;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.entities.Student;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.services.impl.ContactService;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.InstructorService;
import fr.epita.actionlearning.services.impl.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class CourseTest {
    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    /*@Test
    public void AddCourses() throws AlreadyExistingException {

        String name="ActionLearning";

        Time startTime=Time.valueOf("14:00:00");
        Time endTime=Time.valueOf("16:00:00");
        Set<Instructor> instructors=new HashSet<Instructor>();
        instructors.add(instructorService.search("04:27:32:C2:A8:6A:80"));
        Course course=new Course(name,instructors,startTime,endTime);

        courseService.add(course);
    }*/
    @Test
    public void getAll()
    {
        for (Course c: courseService.getAll()) {
            logger.info(c.toString());
        }
    }
}
