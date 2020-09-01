package com.rudolphh.studentscheduler.course.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

public class CourseCreateViewModel extends AndroidViewModel {

    CourseRepository courseRepository;

    public CourseCreateViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
    }

    public void insert(Course course){
        courseRepository.insert(course);
    }

    public void insert(CourseWithMentorAndAssessments course){
        courseRepository.insert(course);
    }
}
