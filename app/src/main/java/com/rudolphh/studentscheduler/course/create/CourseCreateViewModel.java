package com.rudolphh.studentscheduler.course.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.term.database.TermRepository;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.util.List;

public class CourseCreateViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private TermRepository termRepository;

    public CourseCreateViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        termRepository = new TermRepository(application);
    }

    public void insert(Course course){
        courseRepository.insert(course);
    }

    public void insert(CourseWithMentorAndAssessments course){
        courseRepository.insert(course);
    }

    public void update(Course course){
        courseRepository.update(course);
    }


    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course){
        return courseRepository.getCourseById(id_course);
    }

    public LiveData<List<TermWithCourses>> getAllTerms(){
        return termRepository.getAllTerms();
    }

}
