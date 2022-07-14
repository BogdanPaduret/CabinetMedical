package com.company.views;

import com.company.helpers.Utils;
import com.company.repositories.Observed;
import com.company.repositories.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface View extends Observer {

    List<Repository<?>> changedRepositories = new ArrayList<>();

    void play();

    @Override
    default void updateAdd(Observed observed) {
        Utils.addToRepositoryListAfterChange(changedRepositories, observed);
    }

    @Override
    default void updateRemove(Observed observed) {
        Utils.removeFromRepositoryListAfterSave(changedRepositories, observed);
    }
}
