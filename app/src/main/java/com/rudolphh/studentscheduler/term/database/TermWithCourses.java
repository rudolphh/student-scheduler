package com.rudolphh.studentscheduler.term.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.course.database.CourseEntity;

import java.util.List;

public class TermWithCourses {
    @Embedded public TermEntity term;

    @Relation( parentColumn = "id", entityColumn = "termId")
    public List<CourseEntity> courses;
}
