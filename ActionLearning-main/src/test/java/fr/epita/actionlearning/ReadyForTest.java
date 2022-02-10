package fr.epita.actionlearning;

import fr.epita.actionlearning.entities.*;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.services.impl.AttendanceService;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.InstructorService;
import fr.epita.actionlearning.services.impl.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@SpringBootTest
public class ReadyForTest {

    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseService courseService;
    @Autowired
    AttendanceService attendanceService;

    @Test
    public void ready() throws AlreadyExistingException {
        Date date=Date.valueOf("2017-03-16");
        Student student1=new Student("04:04:3E:BA:A8:6A:80",new Contact("Nour","BOU MALHAM","+339848585512",
                "NOUR@email.com",new Address("Lebanon","XXX","SSS","FFF","52")
                ,date),false);
        Student student2=new Student("04:20:35:C2:A8:6A:80",new Contact("Mianhanlang","CHAI","+330908070605",
                "Chai@email.com",new Address("China","AAA","BBB","CCC","50")
                ,date),false);
        Student student3=new Student("04:52:43:EA:FF:6B:80",new Contact("Rawad","Bassil","+331122334455",
                "Rawad@email.com",new Address("CCC","XXD","UJN","POQ","55")
                ,date),false);
        studentService.add(student1);
        studentService.add(student2);
        studentService.add(student3);



        date=Date.valueOf("2022-03-16");
        Instructor instructor=new Instructor("04:27:32:C2:A8:6A:80","1234",
                new Contact("Jad", "Traboulsi", "+330601020304", "jad@email.com", new Address("Lebanon", "Capital", "Berute", "home", "123"),
                        date),
                true);

        instructorService.add(instructor);

        String name="ActionLearning";

        Time startTime=Time.valueOf("14:00:00");
        Time endTime=Time.valueOf("16:00:00");
        Set<Instructor> instructors=new HashSet<Instructor>();
        instructors.add(instructorService.search("04:27:32:C2:A8:6A:80"));
        Course course=new Course(name,instructors,startTime,endTime);

        courseService.add(course);


        Set<Attendance> attendance2 = new HashSet<>();
        for(Course c:courseService.search(name))
        {
            attendance2.add(new Attendance(studentService.search("04:20:35:C2:A8:6A:80"),
                    c, Time.valueOf("00:00:00"),Time.valueOf("00:00:00")));
            attendance2.add(new Attendance(studentService.search("04:52:43:EA:FF:6B:80"),
                    c, Time.valueOf("00:00:00"),Time.valueOf("00:00:00")));
        }

        for (Attendance a: attendance2) {
            attendanceService.add(a);
        }
    }
}
