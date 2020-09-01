package com.rudolphh.studentscheduler.term.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.rudolphh.studentscheduler.term.database.TermEntity;
import com.rudolphh.studentscheduler.term.database.TermRepository;

public class TermCreateViewModel extends AndroidViewModel {

    private TermRepository termRepository;

    public TermCreateViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
    }

    public void insert(TermEntity term) {
        termRepository.insert(term);
    }

}