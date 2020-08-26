package com.rudolphh.studentscheduler.course.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;

import java.util.ArrayList;
import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {

    CourseDetailsViewModel courseDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseDetailsViewModel.class);

        // set up viewModel with liveData
        int courseId = 0;
        String courseTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            courseId = extras.getInt("courseId");
        }

        courseDetailsViewModel.getCourseById(courseId).observe(this, courseDetails -> {
            Objects.requireNonNull(getSupportActionBar()).setTitle(
                    courseDetails.course.getTitle());

            // TODO : set up the field data within textviews etc.
        });

    }







    /*** Up Navigation support */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}