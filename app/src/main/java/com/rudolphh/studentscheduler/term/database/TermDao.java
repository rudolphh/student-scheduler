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

    @Query("DELETE FROM term_table WHERE id_term = :id_term")
    void deleteById(long id_term);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Transaction
    @Query("SELECT * FROM term_table WHERE :id_term = id_term")
    LiveData< TermWithCourses> getTermById(long id_term);

    @Transaction
    @Query("SELECT * FROM term_table")
    LiveData< List<TermWithCourses> > getAllTerms();

    @Transaction
    @Query("SELECT * FROM term_table ORDER BY start ASC")
    LiveData< List<TermWithCourses> > getAllTermsByDate();

}
