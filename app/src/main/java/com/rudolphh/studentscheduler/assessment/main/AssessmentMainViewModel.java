package com.rudolphh.studentscheduler.assessment.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rudolphh.studentscheduler.assessment.database.AssessmentEntity;
import com.rudolphh.studentscheduler.assessment.database.AssessmentRepository;


import java.util.List;

public class AssessmentMainViewModel extends AndroidViewModel{
    
    private AssessmentRepository assessmentRepository;
    private LiveData<List<AssessmentEntity>> allAssessments;

    public AssessmentMainViewModel(@NonNull Application application) {
        super(application);
        assessmentRepository = new AssessmentRepository(application);

        allAssessments = assessmentRepository.getAllAssessments();
    }

    public void insert(AssessmentEntity assessment) {
        assessmentRepository.insert(assessment);
    }

    public void update(AssessmentEntity assessment) {
        assessmentRepository.update(assessment);
    }

    public void delete(AssessmentEntity assessment) {
        assessmentRepository.delete(assessment);
    }

    public void deleteAllAssessments() {
        assessmentRepository.deleteAllAssessments();
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<AssessmentEntity>> getAllAssessmentsByCourseId(int courseId){
        return assessmentRepository.getAllAssessmentsByCourseId(courseId);
    }
        
}
