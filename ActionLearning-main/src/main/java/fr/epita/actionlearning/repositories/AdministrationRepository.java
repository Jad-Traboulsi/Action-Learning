package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Administration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrationRepository extends JpaRepository<Administration,String> {
}
