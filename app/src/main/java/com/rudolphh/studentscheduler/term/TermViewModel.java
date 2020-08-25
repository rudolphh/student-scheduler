package com.rudolphh.studentscheduler.term;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TermRepository termRepository;
    private LiveData<List<TermEntity>> allTerms;

    public TermViewModel(@NonNull Application application) {
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

    public LiveData<List<TermEntity>> getAllTerms(){
        return allTerms;
    }

}
