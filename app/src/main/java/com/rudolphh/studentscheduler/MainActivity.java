package com.rudolphh.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rudolphh.studentscheduler.assessment.AssessmentCreateActivity;
import com.rudolphh.studentscheduler.assessment.main.AssessmentMainActivity;
import com.rudolphh.studentscheduler.course.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;
import com.rudolphh.studentscheduler.term.TermCreateActivity;
import com.rudolphh.studentscheduler.term.main.TermMainActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    FloatingActionButton fabMain, fabTerm, fabCourse, fabAssessment;
    // fabMentor;
    Float translationY = 100f;

    Boolean menuOpen = false;

    OvershootInterpolator interpolator = new OvershootInterpolator();
    private static final String TAG = "MainActivity";

    /** onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFabMenu();

/*        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_mentor_24);
        actionBar.setDisplayUseLogoEnabled(true);*/
    }// end onCreate

    /** initFabMenu */
    private void initFabMenu(){
        fabMain = findViewById(R.id.fabMain);
        fabTerm = findViewById(R.id.fabTerm);
        fabCourse = findViewById(R.id.fabCourse);
        fabAssessment = findViewById(R.id.fabAssessment);
//        fabMentor = findViewById(R.id.fabMentor);

        fabTerm.setAlpha(0f);
        fabCourse.setAlpha(0f);
        fabAssessment.setAlpha(0f);
//        fabMentor.setAlpha(0f);

        fabTerm.setTranslationY(translationY);
        fabCourse.setTranslationY(translationY);
        fabAssessment.setTranslationY(translationY);
//        fabMentor.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabTerm.setOnClickListener(this);
        fabCourse.setOnClickListener(this);
        fabAssessment.setOnClickListener(this);
//        fabMentor.setOnClickListener(this);
    }

    /** Called when the user taps the Terms button */
    public void openTermsView(View view) {
        Intent intent = new Intent(this, TermMainActivity.class);
        startActivity(intent);
    }

    public void openCoursesView(View view) {
        Intent intent = new Intent(this, CourseMainActivity.class);

        startActivity(intent);
    }

    public void openAssessmentsView(View view) {
        Intent intent = new Intent(this, AssessmentMainActivity.class);
        startActivity(intent);
    }

//    public void openMentorsView(View view) {
//        Intent intent = new Intent(this, MentorMainActivity.class);
//        startActivity(intent);
//    }


    ////////////////////////////////////////// Fab Menu Methods

    /** Called when the user taps on fab menu main button */
    private void toggleMenuOpenClose(){
        menuOpen = !menuOpen;

        if(menuOpen){
            fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

            fabTerm.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
            fabCourse.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
            fabAssessment.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
//            fabMentor.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        }
        else {
            fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

            fabTerm.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
            fabCourse.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
            fabAssessment.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
//            fabMentor.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        }
    }

    /** Once the user taps the appropriate fab, start their respective create activity */
    private void openCreateTerm(){
        Intent intent = new Intent(this, TermCreateActivity.class);
        startActivity(intent);
    }

    private void openCreateCourse(){
        Intent intent = new Intent(this, CourseCreateActivity.class);
        startActivity(intent);
    }

    private void openCreateAssessment(){
        Intent intent = new Intent(this, AssessmentCreateActivity.class);
        startActivity(intent);
    }

//    private void openCreateMentor(){
//        Intent intent = new Intent(this, MentorCreateActivity.class);
//        startActivity(intent);
//    }


    /** Fab menu items onClick handler */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fabMain:
                Log.i(TAG, "onClick: fab main");
                break;
            case R.id.fabTerm:
                Log.i(TAG, "onClick: fab term");
                openCreateTerm();
                break;
            case R.id.fabCourse:
                Log.i(TAG, "onClick: fab course");
                openCreateCourse();
                break;
            case R.id.fabAssessment:
                Log.i(TAG, "onClick: fab assessment");
                openCreateAssessment();
                break;
//            case R.id.fabMentor:
//                Log.i(TAG, "onClick: fab mentor");
//                openCreateMentor();
//                break;
        }
        toggleMenuOpenClose();
    }
}