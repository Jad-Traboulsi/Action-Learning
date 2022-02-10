package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Address;
import fr.epita.actionlearning.entities.Administration;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.AdministrationRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdministrationService implements GenericService<Administration> {
    private static final Logger logger = LoggerFactory.getLogger(AdministrationService.class);
    @Autowired
    AdministrationRepository administrationRepository;

    @Override
    public void add(Administration admin) throws AlreadyExistingException {
        if(!hasDuplicate(admin))
        {
            administrationRepository.save(admin);
            logger.info("Admin created");
        }
        else
        {
            throw new AlreadyExistingException("Admin already exists");
        }

    }
    @Override
    public List<Administration> getAll()
    {
        return administrationRepository.findAll();
    }
    @Override
    public boolean hasDuplicate(Administration admin)
    {
        return administrationRepository.findById(admin.getId()).isPresent();
    }
}
