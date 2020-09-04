package com.rudolphh.studentscheduler.mentor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rudolphh.studentscheduler.mentor.database.Mentor;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert
    void insert(Mentor mentor);

    @Update
    void update(Mentor mentor);

    @Delete
    void delete(Mentor mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAllMentors();

    @Query("SELECT * FROM mentor_table ORDER BY name ASC")
    LiveData<List<Mentor>> getAllMentors();

}
