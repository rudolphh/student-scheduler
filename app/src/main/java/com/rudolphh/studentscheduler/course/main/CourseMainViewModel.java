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

    private CourseRepository courseRepository;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    public CourseMainViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);

        allCourses = courseRepository.getAllCourses();
    }

    public void insert(Course Course) {
        courseRepository.insert(Course);
    }

    public void update(Course Course) {
        courseRepository.update(Course);
    }

    public void delete(Course Course) {
        courseRepository.delete(Course);
    }

    public void deleteAllCourses() {
        courseRepository.deleteAllCourses();
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(long termId){
        return courseRepository.getCoursesByTermId(termId);
    }

}