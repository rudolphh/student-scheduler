package com.rudolphh.studentscheduler.assessment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Date goalDate;
    private AssessmentType assessmentType;

    private int courseId;

    public AssessmentEntity(String title, Date goalDate, AssessmentType assessmentType, int courseId) {
        this.title = title;
        this.goalDate = goalDate;
        this.assessmentType = assessmentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
