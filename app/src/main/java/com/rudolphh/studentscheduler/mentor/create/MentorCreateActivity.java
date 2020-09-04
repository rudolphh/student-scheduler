package com.rudolphh.studentscheduler.mentor.create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class MentorCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_create);

        Objects.requireNonNull(getSupportActionBar()).setTitle("New Mentor");

        Toast.makeText(this, "Mentor Create!", Toast.LENGTH_LONG).show();
    }
}