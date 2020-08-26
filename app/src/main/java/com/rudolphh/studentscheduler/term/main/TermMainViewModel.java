package com.rudolphh.studentscheduler.term.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.term.database.TermEntity;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.util.List;

public class TermMainViewModel extends AndroidViewModel {

    private TermRepository termRepository;
    private LiveData<List<TermWithCourses>> allTerms;

    public TermMainViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);

        allTerms = termRepository.getAllTerms();
    }

    public void insert(TermEntity term){
        termRepository.insert(term);
    }

    public void update(TermEntity term){
        termRepository.update(term);
    }

    public void delete(TermEntity term){
        termRepository.delete(term);
    }

    public void deleteAllTerms(){
        termRepository.deleteAllTerms();
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return allTerms;
    }

}
