package com.rudolphh.studentscheduler.mentor;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentor_table")
public class MentorEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String phone;
    private String email;

    // constructor
    public MentorEntity(String name, String phone, String email) {
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
