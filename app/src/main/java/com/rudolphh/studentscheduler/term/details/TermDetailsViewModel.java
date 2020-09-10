package com.rudolphh.studentscheduler.term.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.util.List;


public class TermDetailsViewModel extends AndroidViewModel {

    private TermRepository termRepository;
    private CourseRepository courseRepository;

    public TermDetailsViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
        courseRepository = new CourseRepository(application);
    }

    public LiveData<TermWithCourses> getTermById(long id_term){
        return termRepository.getTermById(id_term);
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(long id_term){
        return courseRepository.getCoursesByTermId(id_term);
    }

    public void deleteTermById(long id_term){
        termRepository.deleteById(id_term);
    }

}