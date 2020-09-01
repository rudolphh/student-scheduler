package com.rudolphh.studentscheduler.course.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CourseCreateActivity extends AppCompatActivity {

    CourseCreateViewModel courseCreateViewModel;

    //UI References
    private EditText editTextTitle;
    private EditText editTextStart;
    private EditText editTextEnd;

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

        String notes = "";

        if(courseTitle.isEmpty()){
            Toast.makeText(this, "Course must have a title", Toast.LENGTH_SHORT).show();
            return;
        }
        // if startDate is after endDate
        assert startDate != null;
        if(startDate.compareTo(endDate) > 0){
            Toast.makeText(this, "Course cannot end before it starts", Toast.LENGTH_SHORT).show();
        } else {
            courseCreateViewModel.insert(new Course(0, courseTitle, startDate, endDate, notes, CourseStatus.IN_PROGRESS));
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
    
    ////////////////// PRIVATE HELPERS

    private void findViewsById() {
        editTextTitle = findViewById(R.id.edit_text_title);

        editTextStart = findViewById(R.id.edit_text_start);
        editTextStart.setInputType(InputType.TYPE_NULL);
        editTextStart.requestFocus();

        editTextEnd = findViewById(R.id.edit_text_end);
        editTextEnd.setInputType(InputType.TYPE_NULL);
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
