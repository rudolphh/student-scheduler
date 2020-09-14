package com.rudolphh.studentscheduler.term.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.course.database.Course;

import java.util.List;

public class TermWithCourses {

    @Embedded
    public Term term;

    @Relation( parentColumn = "id_term", entityColumn = "id_fkterm")
    public List<Course> courses;

    public TermWithCourses(Term term, List<Course> courses){
        this.term = term;
        this.courses = courses;
    }

    @NonNull
    @Override
    public String toString() {
        return term.getTitle();
    }
}
