package com.rudolphh.studentscheduler.assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class AssessmentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Assessments");
    }
}