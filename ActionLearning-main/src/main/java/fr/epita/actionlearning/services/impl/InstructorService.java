package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.InstructorRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InstructorService implements GenericService<Instructor> {
    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    ContactService contactService;


    @Override
    public void add(Instructor instructor) throws AlreadyExistingException {
        if(!hasDuplicate(instructor))
        {
            if (!contactService.hasDuplicate(instructor.getContact())) {
                logger.info("Contact not found, creating contact");
                contactService.add(instructor.getContact());}
            else{
                logger.info("Contact already existing, linking to Instructor");

                instructor.setContact(contactService.search(instructor.getContact()).get(0));
            }
            instructorRepository.save(instructor);
            logger.info("Instructor created");
        }
        else
        {
            throw new AlreadyExistingException("Instructor already exists");
        }

    }


    @Override
    public List<Instructor> getAll()
    {
        return instructorRepository.findAll();
    }
    public Instructor search(String id)
    {
        return instructorRepository.getById(id);
    }

    @Override
    public boolean hasDuplicate(Instructor instructor)
    {
        return instructorRepository.findById(instructor.getId()).isPresent();
    }
}
