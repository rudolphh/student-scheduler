package com.rudolphh.studentscheduler.course.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {

    CourseDetailsViewModel courseDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setToolbarAndNavigation();

        courseDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseDetailsViewModel.class);

        // set up viewModel with liveData
        long id_course = 0;
        String termTitle = "";
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            id_course = extras.getLong("id_course");
            termTitle = extras.getString("termTitle");
        }

        String finalTermTitle = termTitle;
        courseDetailsViewModel.getCourseById(id_course).observe(this, courseDetails -> {
            if(finalTermTitle == null || finalTermTitle.isEmpty()){
                setToolBarTitles(courseDetails.course.getTitle(), "Course Details");
            } else {
                setToolBarTitles(courseDetails.course.getTitle(), finalTermTitle);
            }
            // TODO : set up the field data within textviews etc.
        });

    }// end onCreate




    /////////////////////// Navigation support
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    ////////////////// PRIVATE HELPERS

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