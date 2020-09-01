package com.rudolphh.studentscheduler.course.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.util.List;

public class CourseMainViewModel extends AndroidViewModel {

    private CourseRepository CourseRepository;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    public CourseMainViewModel(@NonNull Application application) {
        super(application);
        CourseRepository = new CourseRepository(application);

        allCourses = CourseRepository.getAllCourses();
    }

    public void insert(Course Course) {
        CourseRepository.insert(Course);
    }

    public void update(Course Course) {
        CourseRepository.update(Course);
    }

    public void delete(Course Course) {
        CourseRepository.delete(Course);
    }

    public void deleteAllCourses() {
        CourseRepository.deleteAllCourses();
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(long termId){
        return CourseRepository.getCoursesByTermId(termId);
    }

}