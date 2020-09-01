package com.rudolphh.studentscheduler.course.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

public class CourseDetailsViewModel extends AndroidViewModel {

    private com.rudolphh.studentscheduler.course.database.CourseRepository CourseRepository;
    private LiveData<CourseWithMentorAndAssessments> course;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        CourseRepository = new CourseRepository(application);
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course){
        return CourseRepository.getCourseById(id_course);
    }

}