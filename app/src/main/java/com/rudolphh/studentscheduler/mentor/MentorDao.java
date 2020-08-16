package com.rudolphh.studentscheduler.mentor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rudolphh.studentscheduler.mentor.MentorEntity;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert
    void insert(MentorEntity mentor);

    @Update
    void update(MentorEntity mentor);

    @Delete
    void delete(MentorEntity mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAllMentors();

    @Query("SELECT * FROM mentor_table ORDER BY name ASC")
    LiveData<List<MentorEntity>> getAllMentors();

}
