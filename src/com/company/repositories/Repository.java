package com.company.repositories;

import com.company.views.Observer;

import java.util.Collection;

public interface Repository<T> extends Observed {

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
