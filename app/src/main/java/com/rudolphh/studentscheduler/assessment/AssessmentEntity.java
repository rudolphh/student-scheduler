package com.rudolphh.studentscheduler.assessment;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.course.database.CourseEntity;

import java.util.Date;

@Entity(tableName = "assessment_table", foreignKeys = @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId"))
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int courseId;

    private String title;
    private Date goalDate;
    private AssessmentType assessmentType;

    public AssessmentEntity(int courseId, String title, Date goalDate, AssessmentType assessmentType) {
        this.courseId = courseId;
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

}
