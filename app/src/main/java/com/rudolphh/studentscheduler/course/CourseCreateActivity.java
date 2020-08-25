package com.rudolphh.studentscheduler.course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class CourseCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_create);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Course");

        Toast.makeText(this, "Course Create!", Toast.LENGTH_LONG).show();
    }
}