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
    void insert(AssessmentEntity assessment);

    @Update
    void update(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY dueDate ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE id = :id")
    LiveData<AssessmentEntity> getAssessmentById(int id);

    @Query("SELECT * FROM assessment_table WHERE courseId = :courseId ORDER BY title")
    LiveData<List<AssessmentEntity>> getAllAssessmentsByCourseId(int courseId);
}
