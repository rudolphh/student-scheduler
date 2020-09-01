package com.rudolphh.studentscheduler.term.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.rudolphh.studentscheduler.course.database.Course;

import java.util.List;

@Dao
public interface TermDao {

    @Transaction
    @Insert
    long insert(Term term);

    @Insert
    void insertCourses(List<Course> courses);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Transaction
    @Query("SELECT * FROM term_table ORDER BY start ASC")
    LiveData< List<TermWithCourses> > getAllTerms();

}
