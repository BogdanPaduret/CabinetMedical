package com.company.repositories;

import com.company.views.Observer;

public interface Observed {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

}
