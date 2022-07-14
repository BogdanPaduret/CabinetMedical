package com.company.views;

import com.company.repositories.Observed;

public interface Observer {

    void updateAdd(Observed observed);

    void updateRemove(Observed observed);

}
