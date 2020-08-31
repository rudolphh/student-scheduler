package com.rudolphh.studentscheduler.assessment.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.course.database.CourseEntity;

import java.util.Date;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId"),
        indices = @Index(value = "courseId", unique = true))
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int courseId;

    private String title;
    private Date dueDate;
    private AssessmentType assessmentType;

    public AssessmentEntity(int courseId, String title, Date dueDate, AssessmentType assessmentType) {
        this.courseId = courseId;
        this.title = title;
        this.dueDate = dueDate;
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

    public Date getDueDate() {
        return dueDate;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

}
