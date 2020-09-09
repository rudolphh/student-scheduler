package com.rudolphh.studentscheduler.course.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rudolphh.studentscheduler.AlertBroadcastReceiver;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.main.AssessmentMainAdapter;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;


public class CourseDetailsActivity extends AppCompatActivity {

    private CourseDetailsViewModel courseDetailsViewModel;

    // UI for main course details
    private TextView tvCourseStartDate;
    private TextView tvCourseEndDate;
    private TextView tvCourseTitle;
    private TextView tvCourseStatus;
    private TextView tvTermTitle;

    // UI for mentor details
    private TextView tvMentorName;
    private TextView tvMentorPhone;
    private TextView tvMentorEmail;

    // UI for course notes
    private TextView tvCourseNotes;

    private ImageView ivStartNotify;
    private ImageView ivEndNotify;

    private SimpleDateFormat dateFormatter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setToolbarAndNavigation();

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.assessment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        AssessmentMainAdapter assessmentMainAdapter = new AssessmentMainAdapter();
        recyclerView.setAdapter(assessmentMainAdapter);

        courseDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseDetailsViewModel.class);

        dateFormatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        findViewsById();

        // set up viewModel with liveData
        long id_course = 0;
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            id_course = extras.getLong("id_course");
        }

        long finalId_course = id_course;
        courseDetailsViewModel.getCourseById(id_course).observe(this, courseDetails -> {

            // set toolbar title
            setToolBarTitles(courseDetails.course.getTitle());

            // set start and end dates
            String startDate = dateFormatter.format(courseDetails.course.getStart());
            tvCourseStartDate.setText(startDate);
            String endDate = dateFormatter.format(courseDetails.course.getAnticipatedEnd());
            tvCourseEndDate.setText(endDate);

            // set course title
            tvCourseTitle.setText(courseDetails.course.getTitle());

            // set course status
            String courseStatus = StatusConverter.fromCourseStatus(courseDetails.course.getCourseStatus());
            tvCourseStatus.setText(courseStatus);

            // if there is a term associated
            long id_term = courseDetails.course.getId_fkterm();
            if(id_term > 0) {
                courseDetailsViewModel.getTermById(id_term).observe(this,
                        termWithCourses -> tvTermTitle.setText(termWithCourses.term.getTitle()));

            } else tvTermTitle.setText("none");

            // set mentor fields
            tvMentorName.setText(courseDetails.mentor.getName());
            tvMentorPhone.setText(courseDetails.mentor.getPhone());
            tvMentorEmail.setText(courseDetails.mentor.getEmail());

            // set course notes
            tvCourseNotes.setText(courseDetails.course.getNotes());

            assessmentMainAdapter.setAssessments(courseDetails.assessments);


            // when user clicks on start date notification icon
            ivStartNotify.setOnClickListener(view->{
                Toast.makeText(this, "Course start notification set", Toast.LENGTH_SHORT).show();

                int notificationId = Integer.parseInt( "200" + finalId_course);

                // create intent and set bundle of extras
                Intent intent = new Intent(this, AlertBroadcastReceiver.class);
                if(extras != null) {
                    extras.putInt("notification_id", notificationId);
                    extras.putString("course_title", courseDetails.course.getTitle());
                    intent.putExtras(extras);
                }

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                        notificationId, intent, 0);

                AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, courseDetails.course.getStart().getTime(), pendingIntent);
            });

            // when user clicks on end date notification icon
            ivEndNotify.setOnClickListener(view->{
                Toast.makeText(this, "Course end notification set", Toast.LENGTH_SHORT).show();

                int notificationId = Integer.parseInt( "300" + finalId_course);

                // create intent and set bundle of extras
                Intent intent = new Intent(this, AlertBroadcastReceiver.class);
                if(extras != null) {
                    extras.putInt("notification_id", notificationId);
                    extras.putString("course_title", courseDetails.course.getTitle());
                    intent.putExtras(extras);
                }

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                        notificationId, intent, 0);

                AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, courseDetails.course.getAnticipatedEnd().getTime(), pendingIntent);
            });

        });// end course observe

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

        tvMentorName = findViewById(R.id.tv_mentor_name);
        tvMentorPhone = findViewById(R.id.tv_mentor_phone);
        tvMentorEmail = findViewById(R.id.tv_mentor_email);

        tvCourseNotes = findViewById(R.id.tv_course_notes);

        ivStartNotify = findViewById(R.id.iv_start_notify);
        ivEndNotify = findViewById(R.id.iv_end_notify);
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
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Course Details");
    }

    private void setToolbarAndNavigation(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}