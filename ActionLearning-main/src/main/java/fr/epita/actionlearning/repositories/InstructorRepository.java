package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor,String> {
}
