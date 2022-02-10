package fr.epita.actionlearning;

import fr.epita.actionlearning.entities.Address;
import fr.epita.actionlearning.entities.Contact;
import fr.epita.actionlearning.entities.Course;
import fr.epita.actionlearning.entities.Instructor;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.services.impl.ContactService;
import fr.epita.actionlearning.services.impl.InstructorService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class InstructorTest {
    @Autowired
    InstructorService instructorService;
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    /*@Test
    public void AddInstructor() throws AlreadyExistingException {


        Date date=Date.valueOf("2022-03-16");
        Instructor instructor=new Instructor("04:27:32:C2:A8:6A:80","1234",
                new Contact("Jad", "Traboulsi", "+330601020304", "jad@email.com", new Address("Lebanon", "Capital", "Berute", "home", "123"),
                        date),
                true);

        instructorService.add(instructor);

    }*/
    @Test
    public void getAll(){

        for (Instructor i:instructorService.getAll()) {
            logger.info(i.toString());
        }
    }
}
