package com.rudolphh.studentscheduler.course.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;

import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

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
        CourseMainViewModel courseMainViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseMainViewModel.class);

        // set up viewModel with liveData
        long id_term = 0;
        String termTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            id_term = extras.getLong("id_term");
            termTitle = extras.getString("termTitle");
        }

        // Either we view ALL COURSES
        if(id_term == 0) {
            courseMainViewModel.getAllCourses().observe(this, courses -> {
                courseMainAdapter.setCourses(courses);
                setToolBarTitles("Courses", "All");
            });

        } else { // OR we view courses for a SPECIFIC TERM
            String finalTermTitle = termTitle;
            courseMainViewModel.getCoursesByTermId(id_term).observe(this, courses -> {
                courseMainAdapter.setCourses(courses);
                setToolBarTitles(finalTermTitle, "Courses");
            });
        }
        
        //////////// set up fab for going to create course activity
        FloatingActionButton fabNewCourse = findViewById(R.id.fab);
        fabNewCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseCreateActivity.class);
            if(extras != null){
                intent.putExtras(extras);
            }
            startActivity(intent);
        });
        
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}