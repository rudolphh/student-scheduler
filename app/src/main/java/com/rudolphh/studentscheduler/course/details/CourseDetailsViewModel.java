package com.rudolphh.studentscheduler.course.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.mentor.database.MentorRepository;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

public class CourseDetailsViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private MentorRepository mentorRepository;
    private TermRepository termRepository;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        termRepository = new TermRepository(application);
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course){
        return courseRepository.getCourseById(id_course);
    }

    public LiveData<TermWithCourses> getTermById(long id_term){
        return termRepository.getTermById(id_term);
    }

    public void deleteCourseById(long id_course){

        courseRepository.deleteById(id_course);
    }

}