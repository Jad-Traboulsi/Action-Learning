package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,String> {
    @Query("SELECT m FROM Contact m WHERE m.birthDate = ?1 AND lower(m.email) = lower(?2) AND  lower(m.lastName) = lower(?3) AND  lower(m.firstName) = lower(?4)")
    List<Contact> searchContact(Date birth_date, String email, String lastName, String firstName);
}
