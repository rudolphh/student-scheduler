package com.rudolphh.studentscheduler.assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class AssessmentCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_create);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Assessment");

        Toast.makeText(this, "Assessment Create!", Toast.LENGTH_LONG).show();
    }
}