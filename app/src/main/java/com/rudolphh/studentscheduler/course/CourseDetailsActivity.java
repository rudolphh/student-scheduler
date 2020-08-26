package com.rudolphh.studentscheduler.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;

import java.util.ArrayList;
import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Single Course");
    }
}