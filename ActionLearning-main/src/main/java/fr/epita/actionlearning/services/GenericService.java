package fr.epita.actionlearning.services;

import fr.epita.actionlearning.exceptions.AlreadyExistingException;

import java.util.List;

public interface GenericService<T>{
    void add(T obj) throws AlreadyExistingException;
    List<T> getAll();
    boolean hasDuplicate(T obj);
}
