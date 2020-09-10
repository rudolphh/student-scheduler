package com.rudolphh.studentscheduler.assessment.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.assessment.database.AssessmentRepository;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.util.List;


public class AssessmentCreateViewModel extends AndroidViewModel {

    private AssessmentRepository assessmentRepository;
    private CourseRepository courseRepository;

    public AssessmentCreateViewModel(@NonNull Application application) {
        super(application);

        assessmentRepository = new AssessmentRepository(application);
        courseRepository = new CourseRepository(application);
    }

    public void insert(Assessment assessment){
        assessmentRepository.insert(assessment);
    }

    public void update(Assessment assessment) { assessmentRepository.update(assessment); }

    public LiveData<Assessment> getAssessmentById(long id_assessment){
        return assessmentRepository.getAssessmentById(id_assessment);
    }

    public LiveData<List<CourseWithMentorAndAssessments>> getAllCourses() {
        return courseRepository.getAllCourses();
    }

}
