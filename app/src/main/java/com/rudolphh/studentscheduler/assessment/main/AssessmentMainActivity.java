package com.rudolphh.studentscheduler.assessment.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.create.AssessmentCreateActivity;

import java.util.Objects;

public class AssessmentMainActivity extends AppCompatActivity {

    private AssessmentMainViewModel assessmentMainViewModel;
    private Toolbar toolbar;
    private FloatingActionButton fabNewAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_main);

        setToolbarAndNavigation();

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
        long courseId = 0;
        String courseTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            courseId = extras.getLong("id_course");
            courseTitle = extras.getString("course_title");
        }

        // Either we view ALL ASSESSMENTS
        if(courseId == 0) {
            assessmentMainViewModel.getAllAssessments().observe(this, assessments -> {
                assessmentMainAdapter.setAssessments(assessments);
                setToolBarTitles("Assessments", "All");
            });

        } else { // OR we view assessments for a SPECIFIC COURSE
            String finalCourseTitle = courseTitle;
            assessmentMainViewModel.getAllAssessmentsByCourseId(courseId).observe(
                    this, assessments -> {
                assessmentMainAdapter.setAssessments(assessments);
                setToolBarTitles(finalCourseTitle, "Assessments");
            });
        }

        //////////// set up fab for going to create course activity
        fabNewAssessment = findViewById(R.id.fab);
        fabNewAssessment.setOnClickListener(view -> {
            Intent intent = new Intent(this, AssessmentCreateActivity.class);
            if(extras != null){
                intent.putExtras(extras);
            }
            startActivity(intent);
        });

    }

    ////////////////// Navigation support

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