package com.rudolphh.studentscheduler.course.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.mentor.database.Mentor;

import java.util.List;

public class CourseWithMentorAndAssessments {

    @Embedded
    public Course course;

    @Relation( parentColumn = "id_course", entityColumn = "id_fkcourse")
    public Mentor mentor;

    @Relation( parentColumn = "id_course", entityColumn = "id_fkcourse")
    public List<Assessment> assessments;

    public CourseWithMentorAndAssessments(Course course, Mentor mentor, List<Assessment> assessments){
        this.course = course;
        this.mentor = mentor;
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public String toString() {
        return course.getTitle();
    }
}
