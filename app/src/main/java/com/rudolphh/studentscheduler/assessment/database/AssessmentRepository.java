package com.rudolphh.studentscheduler.assessment.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;


import java.util.List;

public class AssessmentRepository {

    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentRepository(Application application){

        // get the database
        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();

    }// end constructor

    public void insert(Assessment assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.insert(assessment));
    }

    public void update(Assessment assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.update(assessment));
    }

    public void delete(Assessment assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.delete(assessment));
    }

    public void deleteById(long id_assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.deleteById(id_assessment));
    }

    public void deleteAllAssessments(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.deleteAllAssessments());
    }

    public LiveData<List<Assessment>> getAllAssessments(){
        return allAssessments;
    }

    public LiveData<Assessment> getAssessmentById(long id_assessment) {
        return assessmentDao.getAssessmentById(id_assessment);
    }

    public LiveData<List<Assessment>> getAllAssessmentsByCourseId(long courseId) {
        return assessmentDao.getAllAssessmentsByCourseId(courseId);
    }
}
