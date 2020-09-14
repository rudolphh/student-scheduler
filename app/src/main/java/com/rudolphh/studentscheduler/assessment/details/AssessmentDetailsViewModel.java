package com.rudolphh.studentscheduler.assessment.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.assessment.database.AssessmentRepository;
import com.rudolphh.studentscheduler.course.database.CourseRepository;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

public class AssessmentDetailsViewModel extends AndroidViewModel {

    private AssessmentRepository assessmentRepository;
    private CourseRepository courseRepository;

    public AssessmentDetailsViewModel(@NonNull Application application) {
        super(application);
        assessmentRepository = new AssessmentRepository(application);
        courseRepository = new CourseRepository(application);
    }

    public LiveData<Assessment> getAssessmentById(long id_assessment){
        return assessmentRepository.getAssessmentById(id_assessment);
    }

    public LiveData<CourseWithMentorAndAssessments> getCourseById(long id_course){
        return courseRepository.getCourseById(id_course);
    }

    public void deleteAssessmentById(long id_assessment){
        assessmentRepository.deleteById(id_assessment);
    }

}
