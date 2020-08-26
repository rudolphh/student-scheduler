package com.rudolphh.studentscheduler.term.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;

import java.util.List;

public class TermRepository {

    private TermDao termDao;
    private LiveData<List<TermWithCourses>> allTerms;

    public TermRepository(Application application){

        // get the database
        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();

    }// end constructor

    public void insert(TermEntity term){
        /*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                termDao.insert(term);
            }
        };
        StudentSchedulerDatabase.databaseWriteExecutor.execute(runnable);
        */
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.insert(term));
    }

    public void update(TermEntity term){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.update(term));
    }

    public void delete(TermEntity term){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.delete(term));
    }

    public void deleteAllTerms(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.deleteAllTerms());
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return allTerms;
    }

}


