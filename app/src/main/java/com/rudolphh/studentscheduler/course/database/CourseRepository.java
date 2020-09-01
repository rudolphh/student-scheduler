package com.rudolphh.studentscheduler.course.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;

import java.util.List;

public class CourseRepository {

    private CourseDao courseDao;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    public CourseRepository(Application application){

        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

    }// end constructor

    ///////////////////// insert
    public void insert(Course course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.insert(course));
    }

    public void insert(CourseWithMentorAndAssessments course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.insertMentorWithAssessments(course.mentor, course.assessments));
    }

    /////////////////// update
    public void update(Course course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.update(course));
    }

    /////////////////// delete
    public void delete(Course course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.delete(course));
    }

    public void deleteAllCourses(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.deleteAllCourses());
    }

    ///////////////////// getters
    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses(){
        return allCourses;
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(long id_term) {
        return courseDao.getCoursesByTermId(id_term);
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course) {
        return courseDao.getCourseById(id_course);
    }
}