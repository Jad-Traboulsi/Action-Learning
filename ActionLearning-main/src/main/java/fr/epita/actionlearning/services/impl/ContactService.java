package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Administration;
import fr.epita.actionlearning.entities.Contact;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.ContactRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContactService implements GenericService<Contact> {
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AddressService addressService;

    @Override
    public void add(Contact contact) throws AlreadyExistingException {
        if(!hasDuplicate(contact))
        {
            if (!addressService.hasDuplicate(contact.getAddress())) {
                logger.info("Address not found, creating address");
                addressService.add(contact.getAddress());

            }else{
                logger.info("Address already existing, linking to contact");

                contact.setAddress(addressService.search(contact.getAddress()).get(0));
            }

            contactRepository.save(contact);
            logger.info("Contact saved");

        }
        else
        {
            throw new AlreadyExistingException("Contact already exists");
        }

    }

    public List<Contact> search(Contact c) {
        return contactRepository.searchContact(c.getBirthDate(), c.getEmail(), c.getLastName(), c.getFirstName());
    }

    @Override
    public List<Contact> getAll()
    {
        return contactRepository.findAll();
    }
    @Override
    public boolean hasDuplicate(Contact contact)
    {
        return contactRepository.findById(contact.getEmail()).isPresent();
    }
}
