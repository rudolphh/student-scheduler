package com.rudolphh.studentscheduler.term.create;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.database.Course;
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


public class TermCreateActivity extends AppCompatActivity {

    TermCreateViewModel termCreateViewModel;

    //UI References
    private EditText editTextTitle;
    private EditText editTextStart;
    private EditText editTextEnd;

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Bundle extras;

    long id_term;
    LiveData<TermWithCourses> termLiveData;

    ////////////////////////////// onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create);

        setToolbarAndNavigation();


        // get viewModel instance
        termCreateViewModel = new ViewModelProvider.AndroidViewModelFactory(
                this.getApplication()).create(TermCreateViewModel.class);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();
        //setDefaultDates();
        setDateTimeField();

        extras = getIntent().getExtras();
        id_term = 0;
        if(extras != null){
            id_term = extras.getLong("id_term");
        }

        // if we have a term id, then we are editing
        if(id_term > 0){

            termLiveData = termCreateViewModel.getTermById(id_term);
            termLiveData.observe(this, termDetails -> {

                String termTitle = termDetails.term.getTitle();
                setToolBarTitles("Edit Term", termTitle);
                editTextTitle.setText(termTitle);

                String startDate = dateFormatter.format(termDetails.term.getStart());
                editTextStart.setText(startDate);

                String endDate = dateFormatter.format(termDetails.term.getEnd());
                editTextEnd.setText(endDate);
            });

        } else { // else we are creating a new term
            setToolBarTitles("New Term", "");
        }

    }

    ////////////////////////////////////

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

    public void onClick(View view) {
        if(view == editTextStart) {
            startDatePickerDialog.show();
        } else if(view == editTextEnd) {
            endDatePickerDialog.show();
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
                    saveTerm();
                } catch (ParseException e) {
                    Toast.makeText(this, "Term must have start and end dates", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveTerm() throws ParseException {

        String termTitle = editTextTitle.getText().toString();
        Date startDate = dateFormatter.parse(editTextStart.getText().toString());
        Date endDate = dateFormatter.parse(editTextEnd.getText().toString());

        if(termTitle.isEmpty()){
            Toast.makeText(this, "Term must have a title", Toast.LENGTH_SHORT).show();
            return;
        }
        // if startDate is after endDate
        assert startDate != null;
        if(startDate.compareTo(endDate) > 0){
            Toast.makeText(this, "Term cannot end before it starts", Toast.LENGTH_SHORT).show();
        } else {
            Term newTerm = new Term(termTitle, startDate, endDate);
            List<Course> newTermCourses = new ArrayList<>();

            // if doing an edit, update
            if(extras != null && extras.getLong("id_term") > 0){
                newTerm.setId_term(extras.getLong("id_term"));
                termCreateViewModel.update(new TermWithCourses(newTerm, newTermCourses));
            } else {// else insert
                termCreateViewModel.insert(new TermWithCourses(newTerm, newTermCourses));
                Toast.makeText(this, "Term created successfully", Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }


    ////////////////// PRIVATE HELPERS

    private void setToolBarTitles (String title, String subtitle){
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