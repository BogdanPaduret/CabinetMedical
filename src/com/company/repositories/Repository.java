package com.company.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Repository<T> {

    //create
    void save();
    void add(T data);

    //read
    void load();

    int size();

    T get(int id);
    Collection<T> getAll();

    //update
    void set(int id, T data);
    void addAll(Collection<T> collection);

    //delete
    void clear();
    void remove(T data);

}
