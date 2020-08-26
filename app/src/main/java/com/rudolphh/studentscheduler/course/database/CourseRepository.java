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

    public void insert(CourseEntity course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.insert(course));
    }

    public void update(CourseEntity course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.update(course));
    }

    public void delete(CourseEntity course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.delete(course));
    }

    public void deleteAllCourses(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.deleteAllCourses());
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses(){
        return allCourses;
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(int termId) {
        return courseDao.getCoursesByTermId(termId);
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseById(int courseId) {
        return courseDao.getCourseById(courseId);
    }
}