package fr.epita.actionlearning;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "fr.epita.actionlearning.repositories")
@EntityScan(basePackages = "fr.epita.actionlearning.entities")
public class ActionLearningConfig {

}
