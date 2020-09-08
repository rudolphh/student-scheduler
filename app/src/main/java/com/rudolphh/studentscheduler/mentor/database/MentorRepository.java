package com.rudolphh.studentscheduler.mentor.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.StudentSchedulerDatabase;

import java.util.List;

public class MentorRepository {

    private MentorDao mentorDao;
    private LiveData<List<Mentor>> allMentors;

    public MentorRepository(Application application){

        // get the database
        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize LiveData
        mentorDao = database.mentorDao();
        allMentors = mentorDao.getAllMentors();

    }// end constructor

    public void insert(Mentor mentor){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                mentorDao.insert(mentor));
    }

    public void update(Mentor mentor){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                mentorDao.update(mentor));
    }

    public void delete(Mentor mentor){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                mentorDao.delete(mentor));
    }

    public void deleteAllMentors(){
        StudentSchedulerDatabase.databaseWriteExecutor.execute(()->
                mentorDao.deleteAllMentors());
    }

    public LiveData<List<Mentor>> getAllMentors(){
        return allMentors;
    }

    public LiveData<Mentor> getMentorById(long id_mentor) {
        return mentorDao.getMentorById(id_mentor);
    }

    public LiveData<Mentor> getMentorByCourseId(long courseId) {
        return mentorDao.getMentorByCourseId(courseId);
    }

}
