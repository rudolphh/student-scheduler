package com.rudolphh.studentscheduler.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Courses");
    }
}