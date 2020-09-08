package com.rudolphh.studentscheduler.assessment.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.create.AssessmentCreateActivity;
import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.course.details.CourseDetailsViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private AssessmentDetailsViewModel assessmentDetailsViewModel;

    private TextView tvTitle;
    private TextView tvDueDate;
    private TextView tvAssessmentType;
    private ImageView ivEditAssessmentButton;

    private ImageView ivShareButton;
    private TextView tvCourseNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        setToolbarAndNavigation();

        assessmentDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssessmentDetailsViewModel.class);


        SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        findViewsById();

        long id_assessment = 0;
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
        }

        assessmentDetailsViewModel.getAssessmentById(id_assessment).observe(this, assessment -> {

            // set toolbar title
            setToolBarTitles(assessment.getTitle(), "Assessment Details");

            tvTitle.setText(assessment.getTitle());// set title

            tvDueDate.setText(dateFormatter.format(assessment.getDueDate()));

            String assessmentType = assessment.getAssessmentType().toString();
            assessmentType = assessmentType.substring(0,1) + assessmentType.substring(1).toLowerCase();
            tvAssessmentType.setText(assessmentType);

            ivEditAssessmentButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putLong("id_assessment", assessment.getId_assessment());

                Intent intent = new Intent(this, AssessmentCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            // get course notes
            assessmentDetailsViewModel.getCourseById(assessment.getId_fkcourse()).observe(this,
                    courseWithMentorAndAssessments ->
                            tvCourseNotes.setText(courseWithMentorAndAssessments.course.getNotes()));
        });


    }// end onCreate


    private void findViewsById(){
        tvTitle = findViewById(R.id.tv_assessment_title);
        tvDueDate = findViewById(R.id.tv_assessment_due);
        tvAssessmentType = findViewById(R.id.tv_assessment_type);
        tvCourseNotes = findViewById(R.id.tv_course_notes);

        ivEditAssessmentButton = findViewById(R.id.iv_edit_assessment);
    }

    /////////////////////// Navigation support
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    ////////////////// PRIVATE HELPERS

    private void setToolBarTitles(String title, String subtitle){
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