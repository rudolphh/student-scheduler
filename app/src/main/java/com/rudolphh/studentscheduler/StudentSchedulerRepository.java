package com.rudolphh.studentscheduler;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.assessment.AssessmentDao;
import com.rudolphh.studentscheduler.assessment.AssessmentEntity;
import com.rudolphh.studentscheduler.course.database.CourseDao;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.mentor.MentorDao;
import com.rudolphh.studentscheduler.mentor.MentorEntity;

import com.rudolphh.studentscheduler.term.database.TermRepository;

import java.util.List;

public class StudentSchedulerRepository {

    private TermRepository termRepository;

    private CourseDao courseDao;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    private AssessmentDao assessmentDao;
    private LiveData<List<AssessmentEntity>> allAssessments;

    private MentorDao mentorDao;
    private LiveData<List<MentorEntity>> allMentors;

    ///////////////

    public StudentSchedulerRepository(Application application){
        StudentSchedulerDatabase database = StudentSchedulerDatabase.getInstance(application);

        // initialize repository
        termRepository = new TermRepository(application);


        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();

        mentorDao = database.mentorDao();
        allMentors = mentorDao.getAllMentors();

    }// end constructor


}
