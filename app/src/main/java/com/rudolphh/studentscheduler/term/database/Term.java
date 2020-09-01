package com.rudolphh.studentscheduler.term.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private long id_term;
    private String title;
    private Date start;
    private Date end;

    // constructor
    public Term(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    // setter for id (excluded from constructor)
    public void setId_term(long id) {
        this.id_term = id;
    }

    // getters


    public long getId_term() {
        return id_term;
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
