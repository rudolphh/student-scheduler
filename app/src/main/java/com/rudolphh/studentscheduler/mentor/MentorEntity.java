package com.rudolphh.studentscheduler.mentor;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.course.CourseEntity;

@Entity(tableName = "mentor_table", foreignKeys = @ForeignKey(entity = CourseEntity.class, parentColumns = "id", childColumns = "courseId"))
public class MentorEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int courseId;

    private String name;
    private String phone;
    private String email;

    // constructor
    public MentorEntity(int courseId, String name, String phone, String email) {
        this.courseId = courseId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCourseId(){ return courseId; }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
