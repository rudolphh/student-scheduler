package com.rudolphh.studentscheduler.term.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.rudolphh.studentscheduler.term.database.Term;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

public class TermCreateViewModel extends AndroidViewModel {

    private TermRepository termRepository;

    public TermCreateViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
    }

    public void insertTermWithCourses(TermWithCourses termWithCourses){
        termRepository.insert(termWithCourses);
    }

}