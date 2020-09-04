package com.rudolphh.studentscheduler.mentor.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class MentorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Mentors");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}