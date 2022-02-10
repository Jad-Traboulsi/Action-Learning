package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Administration;
import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.entities.Student;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.StudentRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService implements GenericService<Student> {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ContactService contactService;
    @Override
    public void add(Student student) throws AlreadyExistingException {
        if(!hasDuplicate(student))
        {
            if (!contactService.hasDuplicate(student.getContact())){
                logger.info("Contact not found, creating contact");
                contactService.add(student.getContact());
            }
            else{
                logger.info("Contact already existing, linking to Student");

                student.setContact(contactService.search(student.getContact()).get(0));
            }
            studentRepository.save(student);
            logger.info("Student created");
        }
        else
        {
            throw new AlreadyExistingException("Student already exists");
        }

    }
    @Override
    public List<Student> getAll()
    {
        return studentRepository.findAll();
    }

    public Student search(String id)
    {
        return studentRepository.getById(id);
    }

    @Override
    public boolean hasDuplicate(Student student)
    {
        return studentRepository.findById(student.getId()).isPresent();
    }
}
