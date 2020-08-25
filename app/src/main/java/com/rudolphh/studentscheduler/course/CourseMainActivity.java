package com.rudolphh.studentscheduler.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.term.TermMainActivity;

import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Courses");
    }

    @Override
    public Intent getSupportParentActivityIntent(){
        return getParentActivityIntentImplement();
    }

    @Override
    public Intent getParentActivityIntent(){
        return getParentActivityIntentImplement();
    }

    private Intent getParentActivityIntentImplement(){
        Intent intent = null;
        String goToIntent = null;

        Bundle extras = getIntent().getExtras();

        if(extras != null) goToIntent = extras.getString("goto");

        if(goToIntent != null && goToIntent.equals("TermMainActivity")){
            intent = new Intent(this, TermMainActivity.class);

            // set flags to reuse the previous activity instead of creating a new activity instance
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            intent = new Intent(this, MainActivity.class);
            // if not reusing previous activity, pass extras and bundles here
            intent.putExtra("whatever", "extra");
        }
        return intent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}