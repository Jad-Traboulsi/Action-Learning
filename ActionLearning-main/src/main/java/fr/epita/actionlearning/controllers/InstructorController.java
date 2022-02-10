package fr.epita.actionlearning.controllers;

import fr.epita.actionlearning.DTOs.InfoChosenfromCourseDTO;
import fr.epita.actionlearning.DTOs.InstructorDTO;
import fr.epita.actionlearning.DTOs.MessageDTO;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.entities.Student;
import fr.epita.actionlearning.services.impl.CourseService;
import fr.epita.actionlearning.services.impl.InstructorService;
import org.hibernate.annotations.GeneratorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructor")
public class InstructorController
{
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    InstructorService instructorService;
    @Autowired
    CourseService courseService;

    @GetMapping("/getok")
    public ResponseEntity getok()
    {
        MessageDTO dto = new MessageDTO();
        dto.setResponse(200);
        dto.setResponseMsg("OK");
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity sentInstructor(@PathVariable("instructorId")
                                                     String instructorId)
    {
        InstructorDTO instructorDTO=new InstructorDTO();
        instructorDTO.setIsInstructor(false);

        Instructor instructor= new Instructor();
        instructor.setId(instructorId);
        if (instructorService.hasDuplicate(instructor))
        {
            instructorDTO.setIsInstructor(true);
            instructor=instructorService.search(instructor.getId());

            instructorDTO.setPassword(instructor.getPw());
            logger.info(instructor.toString());
            List<Course> courses= courseService.searchCoursesForInstructor(instructor);
            List<InfoChosenfromCourseDTO> info = new ArrayList<InfoChosenfromCourseDTO>();


            for(Course c: courses){
                logger.info(c.toString());
                info.add(new InfoChosenfromCourseDTO(c.getId(),c.getCourseName(),
                        c.getStartTime(),c.getEndTime()));
            }
            instructorDTO.setCourses(info);
            instructorDTO.setUID(instructor.getId());

            return ResponseEntity.status(200).body(instructorDTO);
        }
        else
        {
            return ResponseEntity.status(405).body(instructorDTO);
        }
    }


}
