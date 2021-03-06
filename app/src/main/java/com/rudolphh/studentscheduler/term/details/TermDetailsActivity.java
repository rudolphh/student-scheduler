package com.rudolphh.studentscheduler.term.details;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainAdapter;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;
import com.rudolphh.studentscheduler.term.database.Term;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class TermDetailsActivity extends AppCompatActivity {

    TermDetailsViewModel termDetailsViewModel;

    //UI for term details
    private TextView tvTitle;
    private TextView tvStart;
    private TextView tvEnd;
    private TextView tvNumberCourses;

    private ImageView ivEditTermButton;

    long id_term;
    LiveData<TermWithCourses> termLiveData;
    int numCourses;

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


        findViewsById();

        // set up viewModel with liveData
        id_term = 0;
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            id_term = extras.getLong("id_term");
        }

        termLiveData = termDetailsViewModel.getTermById(id_term);
        termLiveData.observe(this, termWithCourses -> {
            Term term = termWithCourses.term;
            setToolBarTitles(term.getTitle());

            tvTitle.setText(term.getTitle());// set title

            // format text and set start and end Date textView
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

            tvStart.setText(formatter.format(term.getStart()));
            tvEnd.setText(formatter.format(term.getEnd()));

            numCourses = termWithCourses.courses.size();

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
        FloatingActionButton fabNewCourse = findViewById(R.id.fab);
        fabNewCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseCreateActivity.class);
            if(extras != null){
                intent.putExtras(extras);
            }
            startActivity(intent);
        });
    }


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
                deleteTerm();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void deleteTerm() {
        if(termLiveData.hasObservers()){
            termLiveData.removeObservers(this);
            if(numCourses == 0) {
                termDetailsViewModel.deleteTermById(id_term);
                finish();
            } else {
                Toast.makeText(this, "Term has courses associated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ///////////////////////// Private helpers

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

    private void setToolBarTitles(String title){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Term Details");
    }

    private void setToolbarAndNavigation(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}