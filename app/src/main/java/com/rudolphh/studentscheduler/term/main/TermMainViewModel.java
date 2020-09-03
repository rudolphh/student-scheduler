package com.rudolphh.studentscheduler.term.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.term.database.Term;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.util.List;

public class TermMainViewModel extends AndroidViewModel {

    public TermMainViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
        allTerms = termRepository.getAllTerms();
    }

    private TermRepository termRepository;
    private LiveData<List<TermWithCourses>> allTerms;

    public void insertTermWithCourses(TermWithCourses termWithCourses){
        termRepository.insert(termWithCourses);
    }

//    public void insert(Term term){
//        termRepository.insert(term);
//    }

    public void update(TermWithCourses term){
        termRepository.update(term);
    }

    public void delete(Term term){
        termRepository.delete(term);
    }

    public void deleteAllTerms(){
        termRepository.deleteAllTerms();
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return allTerms;
    }

}
