package com.edu.goit.repository.interfaces;


public interface BaseCrudRepository<T,ID> {
    long create(T item);
    boolean update(T item);
    boolean deleteById(ID id);
    T findById(ID id);
}

