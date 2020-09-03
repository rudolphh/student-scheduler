package com.rudolphh.studentscheduler.term.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

public class TermCreateViewModel extends AndroidViewModel {

    private TermRepository termRepository;

    public TermCreateViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
    }

    public void insert(TermWithCourses termWithCourses){
        termRepository.insert(termWithCourses);
    }

    public void update(TermWithCourses termWithCourses){
        termRepository.update(termWithCourses);
    }

    public LiveData<TermWithCourses> getTermById(long id_term){
        return termRepository.getTermById(id_term);
    }

}