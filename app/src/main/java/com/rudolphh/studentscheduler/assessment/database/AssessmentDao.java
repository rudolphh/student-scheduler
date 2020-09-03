package com.rudolphh.studentscheduler.assessment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY dueDate ASC")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE id_assessment = :id_assessment")
    LiveData<Assessment> getAssessmentById(long id_assessment);

    @Query("SELECT * FROM assessment_table WHERE id_fkcourse = :courseId ORDER BY title")
    LiveData<List<Assessment>> getAllAssessmentsByCourseId(long courseId);
}
