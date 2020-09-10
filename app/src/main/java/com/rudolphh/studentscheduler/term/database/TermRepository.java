package com.rudolphh.studentscheduler.term.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;
import com.rudolphh.studentscheduler.course.database.Course;

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

    public void insert(TermWithCourses termWithCourses){

        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->{
            long identifier = termDao.insert(termWithCourses.term);

            for(Course course : termWithCourses.courses){
                course.setId_fkterm(identifier);
            }
            termDao.insertCourses(termWithCourses.courses);
        });
    }

    public void update(TermWithCourses termWithCourses){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.update(termWithCourses.term));
    }

    public void delete(Term term){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.delete(term));
    }

    public void deleteById(long id_term){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.deleteById(id_term));
    }

    public void deleteAllTerms(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> termDao.deleteAllTerms());
    }

    public LiveData<TermWithCourses> getTermById(long id_term){
        return termDao.getTermById(id_term);
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return allTerms;
    }

}

