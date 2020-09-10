package com.rudolphh.studentscheduler.mentor.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.rudolphh.studentscheduler.course.database.Course;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "mentor_table",
        foreignKeys = @ForeignKey
                (entity = Course.class,
                        parentColumns = "id_course",
                        childColumns = "id_fkcourse", onDelete = CASCADE),
        indices = @Index(value = "id_fkcourse", unique = true) )
public class Mentor {

    @PrimaryKey(autoGenerate = true)
    private long id_mentor;

    private long id_fkcourse;

    private String name;
    private String phone;
    private String email;

    // constructor
    public Mentor(long id_fkcourse, String name, String phone, String email) {
        this.id_fkcourse = id_fkcourse;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public long getId_mentor() {
        return id_mentor;
    }

    public void setId_mentor(long id_mentor) {
        this.id_mentor = id_mentor;
    }

    public long getId_fkcourse() {
        return id_fkcourse;
    }

    public void setId_fkcourse(long id_fkcourse) {
        this.id_fkcourse = id_fkcourse;
    }

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
