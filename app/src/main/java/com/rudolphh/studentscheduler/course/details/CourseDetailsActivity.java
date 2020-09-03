package com.rudolphh.studentscheduler.course.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class CourseDetailsActivity extends AppCompatActivity {

    private CourseDetailsViewModel courseDetailsViewModel;

    private TextView tvCourseStartDate;
    private TextView tvCourseEndDate;
    private TextView tvCourseTitle;
    private TextView tvCourseStatus;
    private TextView tvTermTitle;
    private TextView tvCourseNotes;

    private SimpleDateFormat dateFormatter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setToolbarAndNavigation();

        courseDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseDetailsViewModel.class);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();

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

            String startDate = dateFormatter.format(courseDetails.course.getStart());
            tvCourseStartDate.setText(startDate);

            String endDate = dateFormatter.format(courseDetails.course.getAnticipatedEnd());
            tvCourseEndDate.setText(endDate);

            tvCourseTitle.setText(courseDetails.course.getTitle());

            String courseStatus = StatusConverter.fromCourseStatus(courseDetails.course.getCourseStatus());
            tvCourseStatus.setText(courseStatus);

            // if there is a term associated
            if(courseDetails.course.getId_fkterm() > 0) {
                courseDetailsViewModel.getTermById(courseDetails.course.getId_fkterm()).observe(
                        this, termWithCourses -> {
                            tvTermTitle.setText(termWithCourses.term.getTitle());
                            assert extras != null;
                            extras.putLong("id_term", termWithCourses.term.getId_term());
                        });
            } else tvTermTitle.setText("none");


            tvCourseNotes.setText(courseDetails.course.getNotes());

        });

        // edit button click listener
        ImageView edit_button = findViewById(R.id.edit_button);
        edit_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseCreateActivity.class);
            assert extras != null;
            intent.putExtras(extras);
            startActivity(intent);
        });

    }// end onCreate


    private void findViewsById(){
        tvCourseStartDate = findViewById(R.id.tv_start_date);
        tvCourseEndDate = findViewById(R.id.tv_end_date);
        tvCourseTitle = findViewById(R.id.tv_course_title);
        tvCourseStatus = findViewById(R.id.tv_course_status);
        tvTermTitle = findViewById(R.id.tv_term);
        tvCourseNotes = findViewById(R.id.tv_course_notes);
    }


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