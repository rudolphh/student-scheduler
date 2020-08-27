package com.rudolphh.studentscheduler.assessment.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainAdapter;
import com.rudolphh.studentscheduler.course.main.CourseMainViewModel;

import java.util.Objects;

public class AssessmentMainActivity extends AppCompatActivity {

    AssessmentMainViewModel assessmentMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Assessments");

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.assessment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        AssessmentMainAdapter assessmentMainAdapter = new AssessmentMainAdapter();
        recyclerView.setAdapter(assessmentMainAdapter);

        // get viewModel instance
        assessmentMainViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssessmentMainViewModel.class);

        // set up viewModel with liveData
        int courseId = 0;
        String courseTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            courseId = extras.getInt("courseId");
            courseTitle = extras.getString("courseTitle");
        }

        // Either we view ALL COURSES
        if(courseId == 0) {
            assessmentMainViewModel.getAllAssessments().observe(this, assessments -> {
                assessmentMainAdapter.setAssessments(assessments);
                Objects.requireNonNull(getSupportActionBar()).setTitle("All Assessments");
                // update RecyclerView
                Toast.makeText(AssessmentMainActivity.this, "All Assessments", Toast.LENGTH_LONG).show();
            });
        } else { // OR we view courses for a SPECIFIC TERM
            String finalCourseTitle = courseTitle;
            assessmentMainViewModel.getAllAssessmentsByCourseId(courseId).observe(
                    this, assessments -> {
                assessmentMainAdapter.setAssessments(assessments);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Assessments for "+ finalCourseTitle);
                Toast.makeText(AssessmentMainActivity.this,
                        "Assessments for "+ finalCourseTitle, Toast.LENGTH_LONG).show();
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}