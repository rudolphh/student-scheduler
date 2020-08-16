package com.rudolphh.studentscheduler.course;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rudolphh.studentscheduler.converters.DateConverter;
import com.rudolphh.studentscheduler.converters.StatusConverter;

import java.util.Date;

@Entity(tableName = "course_table")
public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Date start;
    private Date anticipatedEnd;
    private CourseStatus courseStatus;

    // constructor
    public CourseEntity(String title, Date start, Date anticipatedEnd, CourseStatus courseStatus) {
        this.title = title;
        this.start = start;
        this.anticipatedEnd = anticipatedEnd;
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

    public String getTitle() {
        return title;
    }

    public Date getStart() {
        return start;
    }

    public Date getAnticipatedEnd() {
        return anticipatedEnd;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }
}
