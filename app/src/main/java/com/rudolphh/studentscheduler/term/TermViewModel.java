package com.rudolphh.studentscheduler.term;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TermRepository repository;
    private LiveData<List<TermEntity>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(TermEntity term){
        repository.insert(term);
    }

    public void update(TermEntity term){
        repository.update(term);
    }

    public void delete(TermEntity term){
        repository.delete(term);
    }

    public void deleteAllTerms(){
        repository.deleteAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms(){
        return allTerms;
    }

}
