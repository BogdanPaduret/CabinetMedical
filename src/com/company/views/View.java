package com.company.views;

import com.company.helpers.Utils;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.util.ArrayList;

public interface View extends Observer {

    ArrayList<Repository<?>> changedRepositories = new ArrayList<>();

    void play();

    @Override
    default void update(Observed observed) {
        Utils.updateRepositoryListAfterChange(changedRepositories, observed);
    }
}
