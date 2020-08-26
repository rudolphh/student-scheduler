package com.rudolphh.studentscheduler.course.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Transaction
    @Query("SELECT * FROM course_table ORDER BY start ASC")
    LiveData<List<CourseWithMentorAndAssessments>> getAllCourses();

    @Transaction
    @Query("SELECT * FROM course_table WHERE termId = :termId ORDER BY start ASC")
    LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(int termId);

    @Transaction
    @Query("SELECT * FROM course_table WHERE id = :courseId")
    LiveData<CourseWithMentorAndAssessments> getCourseById(int courseId);
}
