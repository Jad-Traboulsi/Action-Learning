package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
}
