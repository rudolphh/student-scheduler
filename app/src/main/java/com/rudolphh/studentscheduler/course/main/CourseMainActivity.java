package com.rudolphh.studentscheduler.course.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.CourseDetailsActivity;

import java.util.Objects;

public class CourseMainActivity extends AppCompatActivity {

    CourseMainViewModel courseMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Courses");

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        CourseMainAdapter courseMainAdapter = new CourseMainAdapter();
        recyclerView.setAdapter(courseMainAdapter);

        // get viewModel instance
        courseMainViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(CourseMainViewModel.class);

        // set up viewModel with liveData
        courseMainViewModel.getAllCourses().observe(this, courses -> {
            courseMainAdapter.setCourses(courses);
            // update RecyclerView
            Toast.makeText(CourseMainActivity.this, "Enjoy the main course!", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public Intent getSupportParentActivityIntent(){
        return getParentActivityIntentImplement();
    }

    @Override
    public Intent getParentActivityIntent(){
        return getParentActivityIntentImplement();
    }

    private Intent getParentActivityIntentImplement(){
        Intent intent;
        String goToIntent = null;
        int courseId = 0;

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            goToIntent = extras.getString("goto");
            courseId = extras.getInt("courseId");
        }

        if(goToIntent != null && goToIntent.equals("CourseMainActivity")){
            if(courseId > 0){
                intent = new Intent(this, CourseDetailsActivity.class);
            } else {
                intent = new Intent(this, CourseMainActivity.class);
            }

            // set flags to reuse the previous activity instead of creating a new activity instance
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            intent = new Intent(this, MainActivity.class);
            // if not reusing previous activity, pass extras and bundles here
            intent.putExtra("whatever", "extra");
        }
        return intent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}