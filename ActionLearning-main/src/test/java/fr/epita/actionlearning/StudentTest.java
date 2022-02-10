package fr.epita.actionlearning;

import fr.epita.actionlearning.entities.*;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.services.impl.AttendanceService;
import fr.epita.actionlearning.services.impl.ContactService;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class StudentTest
{
    private static final Logger logger= LoggerFactory.getLogger(StudentTest.class);

    @Autowired
    StudentService studentService;

    @Autowired
    AttendanceService attendanceService;

    /*@Test
    public void AddStudent() throws AlreadyExistingException {
        Date date=Date.valueOf("2017-03-16");
        Student student1=new Student("04:04:3E:BA:A8:6A:80",new Contact("Nour","BOU MALHAM","+339848585512",
                "NOUR@email.com",new Address("Lebanon","XXX","SSS","FFF","52")
                ,date),false);
        Student student2=new Student("04:20:35:C2:A8:6A:80",new Contact("Mianhanlang","CHAI","+330908070605",
                "Chai@email.com",new Address("China","AAA","BBB","CCC","50")
                ,date),false);
        studentService.add(student1);
        studentService.add(student2);
    }*/

    @Test
    public void getAll()
    {
        for (Student s:studentService.getAll()) {
            logger.info(s.toString());
        }
    }

    @Test
    public void getCourseForStudent(){
        Student student = studentService.search("04:04:3E:BA:A8:6A:80");
        logger.info(student.toString());
        logger.info("has these courses");
        for (Course c: attendanceService.searchCourses(student)){
            logger.info(c.toString());
        }
        Student student2 = studentService.search("04:20:35:C2:A8:6A:80");
        logger.info(student2.toString());
        logger.info("has these courses");
        for (Course c: attendanceService.searchCourses(student2)){
            logger.info(c.toString());
        }

    }

}
