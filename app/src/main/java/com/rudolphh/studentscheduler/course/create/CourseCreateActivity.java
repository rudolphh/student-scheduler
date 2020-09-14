package com.rudolphh.studentscheduler.course.create;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.database.Course;
import com.rudolphh.studentscheduler.course.database.CourseStatus;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.mentor.database.Mentor;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;
import com.rudolphh.studentscheduler.term.database.Term;
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

    private EditText etMentorName;
    private EditText etMentorPhone;
    private EditText etMentorEmail;

    private EditText editTextNotes;
    private Spinner spinnerCourseStatus;
    private Spinner spinnerTerm;

    /////////////
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private long id_term = 0;
    private long id_course = 0;
    private long id_mentor = 0;

    Bundle extras;

    LiveData<CourseWithMentorAndAssessments> courseLiveData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_create);

        setToolbarAndNavigation();

        // get viewModel instance
        courseCreateViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(CourseCreateViewModel.class);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        extras = getIntent().getExtras();
        if(extras != null){
            id_course = extras.getLong("id_course");
            // if creating a course specifically for a term
            id_term = extras.getLong("id_term");
        }

        findViewsById();
        //setDefaultDates();
        setDateTimeField();

        /////////// set up course status AND term spinners
        setUpStatusSpinner();
        setUpTermSpinner();

        // check if the user is creating or editing

        // set up viewModel with liveData

        if(id_course > 0){// pre-populate fields for an edit

            // set all fields for the course
            courseLiveData = courseCreateViewModel.getCourseById(id_course);
            courseLiveData.observe(this, courseDetails -> {
                // set the toolbar title
                setToolbarTitles("Edit Course", courseDetails.course.getTitle());

                // set the start and end dates
                String startDate = dateFormatter.format(courseDetails.course.getStart());
                editTextStart.setText(startDate);
                String endDate = dateFormatter.format(courseDetails.course.getAnticipatedEnd());
                editTextEnd.setText(endDate);

                // set the title
                editTextTitle.setText(courseDetails.course.getTitle());

                int courseStatus_position = courseDetails.course.getCourseStatus().getCode();
                spinnerCourseStatus.setSelection(courseStatus_position+1);

                // set mentor details
                id_mentor = courseDetails.mentor.getId_mentor();
                etMentorName.setText(courseDetails.mentor.getName());
                etMentorPhone.setText(courseDetails.mentor.getPhone());
                etMentorEmail.setText(courseDetails.mentor.getEmail());

                // set course notes
                editTextNotes.setText(courseDetails.course.getNotes());
            });
        } else {// otherwise leave the fields blank
            setToolbarTitles("New Course", "");
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
                    saveCourse();
                } catch (ParseException e) {
                    Toast.makeText(this, "Course must have start and end dates", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    /////////////// Save
    private void saveCourse() throws ParseException {

        String courseTitle = editTextTitle.getText().toString();
        Date startDate = dateFormatter.parse(editTextStart.getText().toString());
        Date endDate = dateFormatter.parse(editTextEnd.getText().toString());

        String notes = editTextNotes.getText().toString();

        // course title validate
        if(courseTitle.isEmpty()){
            Toast.makeText(this, "Course must have a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // course status validate
        int status_position = spinnerCourseStatus.getSelectedItemPosition();
        CourseStatus courseStatus;
        if(status_position != 0){
             courseStatus = StatusConverter.toCourseStatus(spinnerCourseStatus.getSelectedItem().toString());
        } else {
            Toast.makeText(this, "Course needs a status", Toast.LENGTH_SHORT).show();
            return;
        }

        // set fk_term of the course with the selected terms id
        int term_position = spinnerTerm.getSelectedItemPosition();
        long new_id_term;
        // if no valid term is selected
        if(term_position == 0 || term_position == spinnerTerm.getCount()-1){
            Toast.makeText(this, "The course must have a term", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // else the fk_term will equal the id of the term selected
            TermWithCourses termWithCourses = (TermWithCourses) spinnerTerm.getSelectedItem();
            new_id_term = termWithCourses.term.getId_term();
        }


        // start date greater than end date validation
        assert startDate != null;
        if(startDate.compareTo(endDate) > 0){
            Toast.makeText(this, "Course cannot end before it starts", Toast.LENGTH_SHORT).show();
        } else {

            // everything is valid, set course values
            Course course = new Course(new_id_term, courseTitle, startDate, endDate, notes, courseStatus);
            Mentor mentor = new Mentor(id_course, etMentorName.getText().toString(),
                    etMentorPhone.getText().toString(), etMentorEmail.getText().toString());
            // if user is editing
            if(id_course > 0) {
                mentor.setId_mentor(id_mentor);// set its primary key
                courseCreateViewModel.update(mentor);
                course.setId_course(id_course);// set its primary key
                courseCreateViewModel.update(course);
                Toast.makeText(this, "Course edited successfully", Toast.LENGTH_SHORT).show();

            } else {// create a new course and mentor - every id_course has matching id_mentor
                courseCreateViewModel.insertWithMentor(course, mentor);
                Toast.makeText(this, "Course created successfully", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }// end saveCourse


    ////////// Date pickers onClick handler
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
                R.layout.spinner_list_bold, status_types);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_bold);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }// end setUpStatusSpinner


    private void setUpTermSpinner(){
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner_term);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<TermWithCourses> terms = new ArrayList<>();

        // Creating adapter for spinner
        ArrayAdapter<TermWithCourses> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_list, terms);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // set the names

        AtomicInteger count = new AtomicInteger(0);

        courseCreateViewModel.getAllTerms().observe(this, termWithCourses -> {
            terms.clear();
            terms.add(new TermWithCourses(new Term("Select Term", new Date(), new Date()), new ArrayList<>()));
            terms.addAll(termWithCourses);
            terms.add(new TermWithCourses(new Term("NEW TERM", new Date(), new Date()), new ArrayList<>()));
            if(count.get() == 0){// first entering
                if(id_term > 0) {

                    for(int i = 0; i < terms.size(); i++){
                        if(terms.get(i).term.getId_term() == id_term){
                            spinnerTerm.setSelection(i);
                        }
                    }

                } else spinnerTerm.setSelection(0);// set to start of spinner for setup

            } else { // else data changed, set to added term
                spinner.setSelection(terms.size()-2);
            }

            dataAdapter.notifyDataSetChanged();
            count.getAndIncrement();
        });

    }// end setUpTermSpinner


    ///////////////////////////////
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

        // start date
        editTextStart = findViewById(R.id.edit_text_start);
        editTextStart.setInputType(InputType.TYPE_NULL);
        editTextStart.requestFocus();

        // end date
        editTextEnd = findViewById(R.id.edit_text_end);
        editTextEnd.setInputType(InputType.TYPE_NULL);

        // title
        editTextTitle = findViewById(R.id.edit_text_title);

        // course status and term spinners
        spinnerCourseStatus = findViewById(R.id.spinner_course_status);
        spinnerTerm = findViewById(R.id.spinner_term);

        // mentor name, phone, email
        etMentorName = findViewById(R.id.et_mentor_name);
        etMentorPhone = findViewById(R.id.et_mentor_phone);
        etMentorEmail = findViewById(R.id.et_mentor_email);

        // course notes
        editTextNotes = findViewById(R.id.edit_text_notes);


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
