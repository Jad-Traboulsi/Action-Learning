package fr.epita.actionlearning.services.impl;

import fr.epita.actionlearning.entities.Address;
import fr.epita.actionlearning.exceptions.AlreadyExistingException;
import fr.epita.actionlearning.repositories.AddressRepository;
import fr.epita.actionlearning.services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements GenericService<Address> {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    @Autowired
    AddressRepository addressRepository;


    @Override
    public void add(Address addr) throws AlreadyExistingException {
        if(!hasDuplicate(addr))
        {
            addressRepository.save(addr);
            logger.info("Address created");
        }
        else
        {
            throw new AlreadyExistingException("Address already exists");
        }

    }
    @Override
    public List<Address> getAll()
    {
        return addressRepository.findAll();
    }
    @Override
    public boolean hasDuplicate(Address addr)
    {
        return !search(addr).isEmpty();
    }


    public List<Address> search(Address addr)
    {
        return addressRepository.searchAddress(addr.getArea(),addr.getCity(),addr.getCountry(),addr.getNumber(),addr.getStreet());
    }
}
