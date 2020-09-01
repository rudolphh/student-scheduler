package com.rudolphh.studentscheduler.course.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.term.database.Term;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private long id_course;
    
    @ForeignKey
            (entity = Term.class,
            parentColumns = "id_term",
            childColumns = "id_fkterm", onDelete = CASCADE)
    private long id_fkterm;

    private String title;
    private Date start;
    private Date anticipatedEnd;
    private String notes;
    private CourseStatus courseStatus;

    // constructors
    public Course(long id_fkterm, String title, Date start, Date anticipatedEnd, String notes,
                  CourseStatus courseStatus) {
        this.id_fkterm = id_fkterm;
        this.title = title;
        this.start = start;
        this.anticipatedEnd = anticipatedEnd;
        this.notes = notes;
        this.courseStatus = courseStatus;
    }


    public void setId_course(long id_course) {
        this.id_course = id_course;
    }

    public void setId_fkterm(long id_fkterm){
        this.id_fkterm = id_fkterm;
    }

    public long getId_course() {
        return id_course;
    }

    public long getId_fkterm() {
        return id_fkterm;
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
