package com.rudolphh.studentscheduler.term.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Date start;
    private Date end;

    // constructor
    public TermEntity(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    // setter for id (excluded from constructor)
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

    public Date getEnd() {
        return end;
    }
}
