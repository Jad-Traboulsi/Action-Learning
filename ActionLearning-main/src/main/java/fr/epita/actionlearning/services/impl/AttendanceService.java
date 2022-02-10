package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Attendance;
import fr.epita.actionlearning.entities.Contact;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Student;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.AttendanceRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService implements GenericService<Attendance> {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    CourseService courseService;

    @Override
    public void add(Attendance attendance) throws AlreadyExistingException {
        if(!hasDuplicate(attendance))
        {
            if(!courseService.hasDuplicate(attendance.getCourse())){
                logger.info("Course not found, creating Course");
                courseService.add(attendance.getCourse());
            }
            else{
                logger.info("Course Already exists, linking to Course");
                attendance.setCourses(courseService.fullSearch(attendance.getCourse()));
            }
            attendanceRepository.save(attendance);
            logger.info("Attendance created");
        }
        else
        {
            throw new AlreadyExistingException("Attendance already exists");
        }
    }
    @Override
    public List<Attendance> getAll()
    {
        return attendanceRepository.findAll();
    }

    @Override
    public boolean hasDuplicate(Attendance attendance)
    {
        return !attendanceRepository.searchAttendance(attendance.getStudent(),attendance.getCourse(),attendance.getEntranceAttendance(),attendance.getQuitAttendance()).isEmpty();
    }

    public List<Course> searchCourses(Student student){
        List<Course> courses = new ArrayList<>();

        List<Attendance> attendances = attendanceRepository.searchStudentCourses(student);
        for(Attendance a : attendances){
            courses.add(a.getCourse());
        }
        return courses;
    }

    public List<Attendance> getAttendancesOfStudent(Student student){
        return  attendanceRepository.searchStudentCourses(student);
    }

    public void updateEntrance(Time time, Student student,Course course){
        attendanceRepository.updateEntrance(time,student,course);
    }
    public void updateQuit(Time time, Student student,Course course){
        attendanceRepository.updateQuit(time,student,course);
    }




}
