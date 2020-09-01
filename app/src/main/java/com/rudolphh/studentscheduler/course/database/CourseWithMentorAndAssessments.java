package com.rudolphh.studentscheduler.course.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.mentor.Mentor;

import java.util.List;

public class CourseWithMentorAndAssessments {

    @Embedded
    public Course course;

    @Relation( parentColumn = "id_course", entityColumn = "id_fkcourse")
    public Mentor mentor;

    @Relation( parentColumn = "id_course", entityColumn = "id_fkcourse")
    public List<Assessment> assessments;
}
