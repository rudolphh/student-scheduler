package com.rudolphh.studentscheduler.course.main;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;


import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

    CourseMainViewModel courseMainViewModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

        setToolbarAndNavigation();

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        CourseMainAdapter courseMainAdapter = new CourseMainAdapter();
        recyclerView.setAdapter(courseMainAdapter);

        // get viewModel instance
        courseMainViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseMainViewModel.class);

        // set up viewModel with liveData
        int termId = 0;
        String termTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            termId = extras.getInt("termId");
            termTitle = extras.getString("termTitle");
        }

        // Either we view ALL COURSES
        if(termId == 0) {
            courseMainViewModel.getAllCourses().observe(this, courses -> {
                courseMainAdapter.setCourses(courses);
                setToolBarTitles("Courses", "All");
                // update RecyclerView
                Toast.makeText(CourseMainActivity.this, "All Courses", Toast.LENGTH_LONG).show();
            });
        } else { // OR we view courses for a SPECIFIC TERM
            String finalTermTitle = termTitle;
            courseMainViewModel.getCoursesByTermId(termId).observe(this, courses -> {
                courseMainAdapter.setCourses(courses);
                courseMainAdapter.setTermTitle(finalTermTitle);
                setToolBarTitles(finalTermTitle, "Courses");
                Toast.makeText(CourseMainActivity.this,
                        "Courses for "+ finalTermTitle, Toast.LENGTH_LONG).show();
            });
        }
    }// end onCreate



    /////////////////// Navigation support
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /////////////////////// Private Helpers

    private void setToolBarTitles (String title, String subtitle){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(subtitle);
    }

    private void setToolbarAndNavigation(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}