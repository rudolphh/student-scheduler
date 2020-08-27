package com.rudolphh.studentscheduler.course.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.assessment.database.AssessmentEntity;
import com.rudolphh.studentscheduler.mentor.MentorEntity;

import java.util.List;

public class CourseWithMentorAndAssessments {

    @Embedded
    public CourseEntity course;

    @Relation( parentColumn = "id", entityColumn = "courseId", entity = MentorEntity.class)
    public MentorEntity mentor;

    @Relation( parentColumn = "id", entityColumn = "courseId", entity = AssessmentEntity.class)
    public List<AssessmentEntity> assessments;
}
