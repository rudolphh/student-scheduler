package com.rudolphh.studentscheduler.course.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.term.database.TermEntity;

import java.util.Date;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(entity = TermEntity.class, parentColumns = "id", childColumns = "termId"),
        indices = {@Index(value = {"termId"})})
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int termId;

    private String title;
    private Date start;
    private Date anticipatedEnd;
    private String notes;
    private CourseStatus courseStatus;

    // constructors
    public CourseEntity(int termId, String title, Date start, Date anticipatedEnd, String notes,
                        CourseStatus courseStatus) {
        this.termId = termId;
        this.title = title;
        this.start = start;
        this.anticipatedEnd = anticipatedEnd;
        this.notes = notes;
        this.courseStatus = courseStatus;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }


    // getters

    public int getId() {
        return id;
    }

    public int getTermId() {
        return termId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStart() {
        return start;
    }

    public Date getAnticipatedEnd() {
        return anticipatedEnd;
    }

    public String getNotes() {
        return notes;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }
}
