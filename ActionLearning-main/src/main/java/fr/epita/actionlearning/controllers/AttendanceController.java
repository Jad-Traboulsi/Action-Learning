package fr.epita.actionlearning.controllers;

import fr.epita.actionlearning.DTOs.MessageDTO;
import fr.epita.actionlearning.entities.Attendance;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Student;
import fr.epita.actionlearning.services.impl.AttendanceService;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Temporal;
import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/attend")
public class AttendanceController {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    StudentService studentService;
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/{courseId}/{studentId}")
    public ResponseEntity attend(@PathVariable ("courseId") String courseId, @PathVariable("studentId") String studentId){
        Student student = studentService.search(studentId);
        List<Attendance> attendances = attendanceService.getAttendancesOfStudent(student);

        MessageDTO msgdto = new MessageDTO();
        for (Attendance a: attendances){
            if(a.getCourse().getId()==Integer.parseInt(courseId)) {
                if(a.getEntranceAttendance().toString().equals("00:00:00"))
                {
                    //valueOf(LocalTime.now())
                    long now = System.currentTimeMillis();
                    Time sqlTime = new Time(now);
                    a.setEntranceAttendance(sqlTime);
                    attendanceService.updateEntrance(sqlTime,student,a.getCourse());
                    String msg = "Entered at " + sqlTime.toString().substring(0,5);
                    msgdto.setResponseMsg(msg);
                    msgdto.setResponse(200);
                    return ResponseEntity.status(200).body(msgdto);
                }
                else if(a.getQuitAttendance().toString().equals("00:00:00")){
                    long now = System.currentTimeMillis();
                    Time sqlTime = new Time(now);
                    a.setQuitAttendance(sqlTime);
                    attendanceService.updateQuit(sqlTime,student,a.getCourse());

                    String msg = "Left at " + sqlTime.toString().substring(0,5);
                    msgdto.setResponseMsg(msg);
                    msgdto.setResponse(200);
                    return ResponseEntity.status(200).body(msgdto);
                }
                else{
                    String msg = "Attended from " + a.getEntranceAttendance().toString().substring(0,5) + "-" + a.getQuitAttendance().toString().substring(0,5);
                    msgdto.setResponseMsg(msg);
                    msgdto.setResponse(500);
                    return ResponseEntity.status(500).body(msgdto) ;
                }
            }
        }
        String message="Not registered in class";
        msgdto.setResponseMsg(message);
        msgdto.setResponse(405);
        return ResponseEntity.status(405).body(msgdto);
    }



}
