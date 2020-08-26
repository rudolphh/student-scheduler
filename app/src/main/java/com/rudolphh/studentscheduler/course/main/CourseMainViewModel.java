package com.rudolphh.studentscheduler.course.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.course.database.CourseEntity;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.util.List;

public class CourseMainViewModel extends AndroidViewModel {

    private com.rudolphh.studentscheduler.course.database.CourseRepository CourseRepository;
    private LiveData<List<CourseWithMentorAndAssessments>> allCourses;

    public CourseMainViewModel(@NonNull Application application) {
        super(application);
        CourseRepository = new CourseRepository(application);

        allCourses = CourseRepository.getAllCourses();
    }

    public void insert(CourseEntity Course) {
        CourseRepository.insert(Course);
    }

    public void update(CourseEntity Course) {
        CourseRepository.update(Course);
    }

    public void delete(CourseEntity Course) {
        CourseRepository.delete(Course);
    }

    public void deleteAllCourses() {
        CourseRepository.deleteAllCourses();
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses() {
        return allCourses;
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseByTermId(int termId){
        return CourseRepository.getCourseByTermId(termId);
    }

}