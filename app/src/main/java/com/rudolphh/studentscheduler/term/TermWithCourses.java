package com.rudolphh.studentscheduler.term;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.rudolphh.studentscheduler.course.CourseEntity;

import java.util.List;

public class TermWithCourses {
    @Embedded public TermEntity term;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public List<CourseEntity> courses;
}
