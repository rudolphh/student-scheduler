package com.rudolphh.studentscheduler.assessment.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.assessment.database.AssessmentType;
import com.rudolphh.studentscheduler.converters.AssessmentTypeConverter;

import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AssessmentCreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AssessmentCreateViewModel assessmentCreateViewModel;

    //UI References
    private EditText editTextTitle;
    private EditText editTextDueDate;

    private Spinner spinnerAssessmentType;
    private Spinner spinnerCourse;

    private DatePickerDialog dueDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private Bundle extras;
    private long id_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_create);

        setToolbarAndNavigation();

        // get viewModel instance
        assessmentCreateViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(AssessmentCreateViewModel.class);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();
        //setDefaultDates();
        setDateTimeField();

        /////////// set up course status AND term spinners
        setUpAssessmentTypeSpinner();
        setUpCourseSpinner();

        long id_assessment = 0;
        extras = getIntent().getExtras();

        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
        }

        if(id_assessment > 0){

            assessmentCreateViewModel.getAssessmentById(id_assessment).observe(this, assessment -> {

                setToolbarTitles("Edit Assessment", assessment.getTitle());

                String dueDate = dateFormatter.format(assessment.getDueDate());
                editTextDueDate.setText(dueDate);

                editTextTitle.setText(assessment.getTitle());

                int assessment_type_position = assessment.getAssessmentType().getCode();
                spinnerAssessmentType.setSelection(assessment_type_position);

                // if there is a course associated
                id_course = assessment.getId_fkcourse();
                Log.i("id_course : ", String.valueOf(id_course));
                if(id_course > 0) {
                    spinnerCourse.setSelection((int)id_course);
                } else spinnerCourse.setSelection(0);

            });
        } else {
            setToolbarTitles("New Assessment", "");
        }

    }


    /////////////////////////////// Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:// case back button

            case R.id.cancel_create:
                // cancel term create
                finish();
                return true;

            case R.id.save_item:
                // save the term
                try {
                    saveAssessment();
                } catch (ParseException e) {
                    Toast.makeText(this, "Assessment must have a due date", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveAssessment() throws ParseException {

        String assessmentTitle = editTextTitle.getText().toString();
        Date dueDate = dateFormatter.parse(editTextDueDate.getText().toString());

        if(assessmentTitle.isEmpty()){
            Toast.makeText(this, "Assessment must have a title", Toast.LENGTH_SHORT).show();
            return;
        }

        int type_position = spinnerAssessmentType.getSelectedItemPosition();
        AssessmentType assessmentType = AssessmentTypeConverter.codeToAssessmentType(type_position);

        int course_position = spinnerCourse.getSelectedItemPosition();
        if(course_position == spinnerCourse.getCount()-1){
            course_position = 0;
        }

        // if editing, get the assessment id from bundle extras
        long id_assessment = 0;
        if(extras != null){
            id_assessment = extras.getLong("id_assessment");
        }

        Assessment assessment = new Assessment(course_position, assessmentTitle, dueDate, assessmentType);
        if(id_assessment > 0){// user is editing
            assessment.setId_assessment(id_assessment);// set the assessment id
            assessmentCreateViewModel.update(assessment);
            Toast.makeText(this, "Assessment edited successfully", Toast.LENGTH_SHORT).show();

        } else {// user is creating
            assessmentCreateViewModel.insert(assessment);
            Toast.makeText(this, "Assessment created successfully", Toast.LENGTH_SHORT).show();
        }

        finish();
        
    }// end saveAssessment

    public void onClick(View view) {
        if(view == editTextDueDate) {
            dueDatePickerDialog.show();
        } 
    }


    /////////////////////////////////////////////// Spinner

    private void setUpAssessmentTypeSpinner(){
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner_assessment_type);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> assessment_types = new ArrayList<>();
        assessment_types.add("Objective");
        assessment_types.add("Performance");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_list, assessment_types);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void setUpCourseSpinner(){
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner_course);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> course_names = new ArrayList<>();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_list, course_names);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // set the names

        AtomicInteger count = new AtomicInteger(0);
        Bundle extras = getIntent().getExtras();
        long courseId = 0;

        if(extras != null){
            courseId = extras.getLong("id_course");
        }

        long finalCourseId = courseId;
        assessmentCreateViewModel.getAllCourses().observe(this, coursesWithMentorAndAssessments -> {
            course_names.clear();
            course_names.add("Select Course");
            for (CourseWithMentorAndAssessments courseDetails : coursesWithMentorAndAssessments){
                course_names.add(courseDetails.course.getTitle());
            }
            course_names.add("NEW COURSE");

            if(count.get() == 0){// if first entering
                if(finalCourseId > 0){// upon entering to create assessment
                    spinner.setSelection((int)finalCourseId);
                } else if (id_course > 0){// upon entering to edit assessment
                    spinner.setSelection((int) id_course);
                } else {
                    spinner.setSelection(0);
                }
            } else {// upon coming back from create course
                spinner.setSelection(course_names.size()-2);
            }

            // if any courses are added, we need to update the spinner
            dataAdapter.notifyDataSetChanged();
            count.getAndIncrement();// increase count of entering activity
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // if "new course" is selected go to course create activity
        if (parent.getId() == R.id.spinner_course) {
            if (position == (parent.getCount() - 1)) {
                Intent intent = new Intent(this, CourseCreateActivity.class);
                startActivity(intent);
            }
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    ////////////////// PRIVATE HELPERS

    private void findViewsById() {
        editTextTitle = findViewById(R.id.edit_text_title);

        editTextDueDate = findViewById(R.id.edit_text_start);
        editTextDueDate.setInputType(InputType.TYPE_NULL);
        editTextDueDate.requestFocus();

        spinnerAssessmentType = findViewById(R.id.spinner_assessment_type);
        spinnerCourse = findViewById(R.id.spinner_course);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        dueDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            editTextDueDate.setText(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        
    }

    private void setToolbarTitles(String title, String subtitle){
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(subtitle);
    }

    private void setToolbarAndNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}