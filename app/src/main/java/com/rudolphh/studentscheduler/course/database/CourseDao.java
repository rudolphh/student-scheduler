package com.rudolphh.studentscheduler.course.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.rudolphh.studentscheduler.mentor.database.Mentor;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    long insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course_table WHERE id_course = :id_course")
    void deleteById(long id_course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Transaction
    @Query("SELECT * FROM course_table")
    LiveData<List<CourseWithMentorAndAssessments>> getAllCourses();

    @Transaction
    @Query("SELECT * FROM course_table ORDER BY start ASC")
    LiveData<List<CourseWithMentorAndAssessments>> getAllCoursesByStart();

    @Transaction
    @Query("SELECT * FROM course_table WHERE id_fkterm = :termId ORDER BY start ASC")
    LiveData<List<CourseWithMentorAndAssessments>> getCoursesByTermId(long termId);

    @Transaction
    @Query("SELECT * FROM course_table WHERE id_course = :courseId")
    LiveData<CourseWithMentorAndAssessments> getCourseById(long courseId);
}
