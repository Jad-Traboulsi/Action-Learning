package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Administration;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.CourseRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService implements GenericService<Course> {
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorService instructorService;
    @Override
    public void add(Course course) throws AlreadyExistingException {
        if(!hasDuplicate(course))
        {
            Set<Instructor> instructors = new HashSet<>();
            for (Instructor i:course.getInstructors()) {
                if (!instructorService.hasDuplicate(i)) {
                    instructorService.add(i);
                    logger.info("Instructor Created");
                    instructors.add(i);
                }
                else{
                    instructors.add(i);
                    logger.info("Instructor found, linking to course");
                }
            }
            course.setInstructors(instructors);
            courseRepository.save(course);
            logger.info("Course created");
        }
        else
        {
            throw new AlreadyExistingException("Course already exists");
        }

    }
    public Course fullSearch(Course course){
        return courseRepository.searchByNameAndTime(course.getCourseName(),course.getStartTime(),course.getEndTime()).get(0);
    }
    public List<Course> search(String courseName)
    {
        return courseRepository.searchByName(courseName);
    }
    public List<Course> searchCoursesForInstructor(Instructor instructor){

        return courseRepository.searchByInstructor(instructor);
    }
    @Override
    public List<Course> getAll()
    {
        return courseRepository.findAll();
    }
    @Override
    public boolean hasDuplicate(Course course)
    {
        return !courseRepository.searchByName(course.getCourseName()).isEmpty();
    }
}
