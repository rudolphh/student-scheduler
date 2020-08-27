package com.rudolphh.studentscheduler.course.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.course.details.CourseDetailsActivity;

import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

    CourseMainViewModel courseMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

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
                Objects.requireNonNull(getSupportActionBar()).setTitle("All Courses");
                // update RecyclerView
                Toast.makeText(CourseMainActivity.this, "All Courses", Toast.LENGTH_LONG).show();
            });
        } else { // OR we view courses for a SPECIFIC TERM
            String finalTermTitle = termTitle;
            courseMainViewModel.getCoursesByTermId(termId).observe(this, courses -> {
                courseMainAdapter.setCourses(courses);
                courseMainAdapter.setTermTitle(finalTermTitle);
                Objects.requireNonNull(getSupportActionBar()).setTitle(finalTermTitle + " Courses");
                Toast.makeText(CourseMainActivity.this,
                        "Courses for "+ finalTermTitle, Toast.LENGTH_LONG).show();
            });
        }
    }// end onCreate

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}