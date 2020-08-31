package com.rudolphh.studentscheduler.term.create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;

import java.util.Objects;

public class TermCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create);

        Objects.requireNonNull(getSupportActionBar()).setTitle("New Term");

        Toast.makeText(this, "Term Create!", Toast.LENGTH_LONG).show();
    }
}