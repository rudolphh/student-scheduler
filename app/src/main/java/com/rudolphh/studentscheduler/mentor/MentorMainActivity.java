package com.rudolphh.studentscheduler.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class MentorMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Mentors");
    }
}