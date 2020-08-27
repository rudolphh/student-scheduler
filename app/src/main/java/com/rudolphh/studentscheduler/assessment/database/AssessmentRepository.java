package com.rudolphh.studentscheduler.assessment.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;


import java.util.List;

public class AssessmentRepository {

    private AssessmentDao assessmentDao;
    private LiveData<List<AssessmentEntity>> allAssessments;

    public AssessmentRepository(Application application){

        // get the database
        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();

    }// end constructor

    public void insert(AssessmentEntity assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.insert(assessment));
    }

    public void update(AssessmentEntity assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.update(assessment));
    }

    public void delete(AssessmentEntity assessment){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.delete(assessment));
    }

    public void deleteAllAssessments(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                assessmentDao.deleteAllAssessments());
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return allAssessments;
    }

    public LiveData<AssessmentEntity> getAssessmentByCourseId(int courseId) {
        return assessmentDao.getAssessmentById(courseId);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsByCourseId(int courseId) {
        return assessmentDao.getAllAssessmentsByCourseId(courseId);
    }
}
