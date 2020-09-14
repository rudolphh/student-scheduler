package com.rudolphh.studentscheduler.assessment.details;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.rudolphh.studentscheduler.AlertBroadcastReceiver;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.create.AssessmentCreateActivity;
import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.course.database.Course;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetailsActivity extends AppCompatActivity {

    private AssessmentDetailsViewModel assessmentDetailsViewModel;

    private TextView tvTitle;
    private TextView tvDueDate;
    private ImageView ivDueNotify;
    private TextView tvAssessmentType;
    private ImageView ivEditAssessmentButton;
    private ImageView ivShareButton;
    private TextView tvCourseNotes;

    Bundle extras;
    long id_assessment;
    LiveData<Assessment> assessmentLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        setToolbarAndNavigation();

        assessmentDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssessmentDetailsViewModel.class);


        SimpleDateFormat dateFormatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        findViewsById();

        id_assessment = 0;
        extras = getIntent().getExtras();

        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
        }

        assessmentLiveData = assessmentDetailsViewModel.getAssessmentById(id_assessment);
        assessmentLiveData.observe(this, assessment -> {

            // set toolbar title
            setToolBarTitles(assessment.getTitle());

            tvTitle.setText(assessment.getTitle());// set title

            tvDueDate.setText(dateFormatter.format(assessment.getDueDate()));

            String assessmentType = assessment.getAssessmentType().toString();
            assessmentType = assessmentType.substring(0,1) + assessmentType.substring(1).toLowerCase();
            tvAssessmentType.setText(assessmentType);

            ivEditAssessmentButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putLong("id_assessment", assessment.getId_assessment());
                bundle.putLong("id_course", assessment.getId_fkcourse());

                Intent intent = new Intent(this, AssessmentCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            // get course notes
            assessmentDetailsViewModel.getCourseById(assessment.getId_fkcourse()).observe(this, courseWithMentorAndAssessments -> {

                Course course = courseWithMentorAndAssessments.course;
                String course_notes = course.getNotes();
                tvCourseNotes.setText(course_notes);


                // when user clicks on share course notes button
                ivShareButton.setOnClickListener(view -> {

                    if(course_notes.isEmpty()){
                        Toast.makeText(this, "No notes to share", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, course.getTitle() + " Course Notes" );
                        sendIntent.putExtra(Intent.EXTRA_TEXT, course_notes);
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    }

                });// end share clickListener

            });// end course observe

            setUpDueDateNotificationClickListener(assessment);

        });// end assessment observe

    }// end onCreate


    /////////////////////////////// Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:// case back button
                finish();
                return true;

            case R.id.delete_item:
                deleteAssessment();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAssessment() {

        if(assessmentLiveData.hasObservers()){
            assessmentLiveData.removeObservers(this);
            assessmentDetailsViewModel.deleteAssessmentById(id_assessment);
            finish();
        }
    }


    //////////////////////// private helpers

    private void setUpDueDateNotificationClickListener(Assessment assessment){
        // when user clicks on due date notification icon
        ivDueNotify.setOnClickListener(view->{
            Toast.makeText(this, "Assessment due date notification set", Toast.LENGTH_SHORT).show();

            int notificationId = Integer.parseInt( "100" + assessment.getId_assessment());

            Intent intent = new Intent(this, AlertBroadcastReceiver.class);

            if(extras != null) {
                extras.putInt("notification_id", notificationId);
                extras.putString("assessment_title", assessment.getTitle());
                intent.putExtras(extras);
            }

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    Integer.parseInt("100"+assessment.getId_assessment()), intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, assessment.getDueDate().getTime(), pendingIntent);
        });
    }

    private void findViewsById(){
        tvTitle = findViewById(R.id.tv_assessment_title);
        tvDueDate = findViewById(R.id.tv_assessment_due);
        ivDueNotify = findViewById(R.id.iv_due_notify);
        tvAssessmentType = findViewById(R.id.tv_assessment_type);
        tvCourseNotes = findViewById(R.id.tv_course_notes);

        ivEditAssessmentButton = findViewById(R.id.iv_edit_assessment);
        ivShareButton = findViewById(R.id.iv_notes_share);
    }

    /////////////////////// Navigation support
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    ////////////////// PRIVATE HELPERS

    private void setToolBarTitles(String title){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Assessment Details");
    }

    private void setToolbarAndNavigation(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}