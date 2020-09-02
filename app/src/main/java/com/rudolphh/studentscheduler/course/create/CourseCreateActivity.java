package com.rudolphh.studentscheduler.course.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseStatus;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CourseCreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CourseCreateViewModel courseCreateViewModel;

    //UI References
    private EditText editTextTitle;
    private EditText editTextStart;
    private EditText editTextEnd;

    private EditText editTextNotes;
    private Spinner spinnerCourseStatus;
    private Spinner spinnerTerm;

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_create);

        setToolbarAndNavigation();

        // get viewModel instance
        courseCreateViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseCreateViewModel.class);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();
        //setDefaultDates();
        setDateTimeField();

        /////////// set up course status AND term spinners
        setUpStatusSpinner();
        setUpTermSpinner();
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
                    saveCourse();
                } catch (ParseException e) {
                    Toast.makeText(this, "Course must have start and end dates", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveCourse() throws ParseException {

        String courseTitle = editTextTitle.getText().toString();
        Date startDate = dateFormatter.parse(editTextStart.getText().toString());
        Date endDate = dateFormatter.parse(editTextEnd.getText().toString());

        String notes = editTextNotes.getText().toString();

        if(courseTitle.isEmpty()){
            Toast.makeText(this, "Course must have a title", Toast.LENGTH_SHORT).show();
            return;
        }

        int status_position = spinnerCourseStatus.getSelectedItemPosition();
        CourseStatus courseStatus;
        if(status_position != 0){
             courseStatus = StatusConverter.toCourseStatus(spinnerCourseStatus.getSelectedItem().toString());
        } else {
            Toast.makeText(this, "Course needs a status", Toast.LENGTH_SHORT).show();
            return;
        }

        int term_position = spinnerTerm.getSelectedItemPosition();
        if(term_position == spinnerTerm.getCount()-1){
            term_position = 0;
        }

        // if startDate is after endDate
        assert startDate != null;
        if(startDate.compareTo(endDate) > 0){
            Toast.makeText(this, "Course cannot end before it starts", Toast.LENGTH_SHORT).show();
        } else {
            courseCreateViewModel.insert(new Course(term_position, courseTitle, startDate, endDate, notes, courseStatus));
            Toast.makeText(this, "Course created successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onClick(View view) {
        if(view == editTextStart) {
            startDatePickerDialog.show();
        } else if(view == editTextEnd) {
            endDatePickerDialog.show();
        }
    }


    /////////////////////////////////////////////// Spinner

    private void setUpStatusSpinner(){
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner_course_status);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> status_types = new ArrayList<>();
        status_types.add("Select Status");
        status_types.add("In Progress");
        status_types.add("Completed");
        status_types.add("Dropped");
        status_types.add("Plan to Take");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_list, status_types);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void setUpTermSpinner(){
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner_term);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> term_names = new ArrayList<>();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_list, term_names);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // set the names

        AtomicInteger count = new AtomicInteger(0);
        Bundle extras = getIntent().getExtras();
        long termId = 0;

        if(extras != null){
            termId = extras.getLong("id_term");
        }

        long finalTermId = termId;
        courseCreateViewModel.getAllTerms().observe(this, termWithCourses -> {
            term_names.clear();
            term_names.add("Select Term");
            for (TermWithCourses termDetails : termWithCourses){
                term_names.add(termDetails.term.getTitle());
            }
            term_names.add("NEW TERM");
            if(count.get() == 0){// if first entering
                if(finalTermId > 0){
                    spinner.setSelection((int)finalTermId);
                } else {
                    spinner.setSelection(0);
                }
            } else {
                spinner.setSelection(term_names.size()-2);
            }

            dataAdapter.notifyDataSetChanged();
            count.getAndIncrement();
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // if "new term" is selected go to term create activity
        if (parent.getId() == R.id.spinner_term) {
            if (position == (parent.getCount() - 1)) {
                Intent intent = new Intent(this, TermCreateActivity.class);
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

        editTextStart = findViewById(R.id.edit_text_start);
        editTextStart.setInputType(InputType.TYPE_NULL);
        editTextStart.requestFocus();

        editTextEnd = findViewById(R.id.edit_text_end);
        editTextEnd.setInputType(InputType.TYPE_NULL);

        editTextNotes = findViewById(R.id.edit_text_notes);

        spinnerCourseStatus = findViewById(R.id.spinner_course_status);
        spinnerTerm = findViewById(R.id.spinner_term);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            editTextStart.setText(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        newCalendar.add(Calendar.DATE, 3);// set default end date 3 days later
        endDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            editTextEnd.setText(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void setToolbarAndNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("New Course");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
