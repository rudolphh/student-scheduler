package com.rudolphh.studentscheduler.assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.course.database.Course;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "assessment_table",
        indices = @Index(value = "id_fkcourse"),
        foreignKeys = @ForeignKey
                (entity = Course.class,
                        parentColumns = "id_course",
                        childColumns = "id_fkcourse", onDelete = CASCADE))
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private long id_assessment;

    private long id_fkcourse;

    private String title;
    private Date dueDate;
    private AssessmentType assessmentType;

    public Assessment(long id_fkcourse, String title, Date dueDate, AssessmentType assessmentType) {
        this.id_fkcourse = id_fkcourse;
        this.title = title;
        this.dueDate = dueDate;
        this.assessmentType = assessmentType;
    }

    public long getId_assessment() {
        return id_assessment;
    }

    public void setId_assessment(long id_assessment) {
        this.id_assessment = id_assessment;
    }

    public void setId_fkcourse(long id_fkcourse){
        this.id_fkcourse = id_fkcourse;
    }

    public long getId_fkcourse(){
        return id_fkcourse;
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

    public long getCourseId() {
        return id_fkcourse;
    }

}
