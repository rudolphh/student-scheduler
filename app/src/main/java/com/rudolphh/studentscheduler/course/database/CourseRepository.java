package com.rudolphh.studentscheduler.course.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;
import com.rudolphh.studentscheduler.mentor.database.Mentor;
import com.rudolphh.studentscheduler.mentor.database.MentorDao;

import java.util.List;

public class CourseRepository {

    private CourseDao courseDao;
    private MentorDao mentorDao;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    public CourseRepository(Application application){

        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        courseDao = database.courseDao();
        mentorDao = database.mentorDao();
        allCourses = courseDao.getAllCourses();

    }// end constructor

    ///////////////////// insert
    public void insert(Course course){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> courseDao.insert(course));
    }

    public void insertWithMentor(Course course, Mentor mentor){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()-> {
            long id_course = courseDao.insert(course);
            mentor.setId_fkcourse(id_course);
            mentorDao.insert(mentor);
        });
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