package com.rudolphh.studentscheduler.term.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudolphh.studentscheduler.R;

import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainAdapter;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;
import com.rudolphh.studentscheduler.term.database.Term;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class TermDetailsActivity extends AppCompatActivity {

    private TermDetailsViewModel termDetailsViewModel;

    //UI for term details
    private TextView tvTitle;
    private TextView tvStart;
    private TextView tvEnd;
    private TextView tvNumberCourses;

    private ImageView ivEditTermButton;

    private FloatingActionButton fabNewCourse;

    /////////////////// onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        setToolbarAndNavigation();

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        CourseMainAdapter courseMainAdapter = new CourseMainAdapter();
        recyclerView.setAdapter(courseMainAdapter);

        // get viewModel instance
        termDetailsViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(TermDetailsViewModel.class);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();

        // set up viewModel with liveData
        long id_term = 0;
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            id_term = extras.getLong("id_term");
        }

        termDetailsViewModel.getTermById(id_term).observe(this, termWithCourses -> {
            Term term = termWithCourses.term;
            setToolBarTitles(term.getTitle(), "Term Details");

            tvTitle.setText(term.getTitle());// set title

            // format text and set start and end Date textView
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

            tvStart.setText(formatter.format(term.getStart()));
            tvEnd.setText(formatter.format(term.getEnd()));

            int numCourses = termWithCourses.courses.size();

            String numberOfCourses =  (numCourses == 1) ? numCourses + " course" : numCourses + " courses";
            tvNumberCourses.setText(numberOfCourses);

            ivEditTermButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putLong("id_term", termWithCourses.term.getId_term());

                Intent intent = new Intent(this, TermCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        });

        termDetailsViewModel.getCoursesByTermId(id_term).observe(this, courseMainAdapter::setCourses);

        // edit button click listener
        ImageView edit_button = findViewById(R.id.edit_term_button);
        edit_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, TermCreateActivity.class);
            assert extras != null;
            intent.putExtras(extras);
            startActivity(intent);
        });

        //////////// set up fab for going to create course activity
        fabNewCourse = findViewById(R.id.fab);
        fabNewCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseCreateActivity.class);
            if(extras != null){
                intent.putExtras(extras);
            }
            startActivity(intent);
        });
    }


    private void findViewsById(){
        tvTitle = findViewById(R.id.text_view_title);
        tvStart = findViewById(R.id.text_view_start);
        tvEnd = findViewById(R.id.text_view_end);
        tvNumberCourses = findViewById(R.id.text_view_number_courses);
        ivEditTermButton = findViewById(R.id.edit_term_button);
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