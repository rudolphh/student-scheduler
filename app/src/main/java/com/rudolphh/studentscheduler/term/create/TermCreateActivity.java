package com.rudolphh.studentscheduler.term.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class TermCreateActivity extends AppCompatActivity {

    //UI References
    private EditText editTextStart;
    private EditText editTextEnd;

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Term");

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        findViewsById();
        //setDefaultDates();
        setDateTimeField();

        Toast.makeText(this, "Term Create!", Toast.LENGTH_LONG).show();
    }

    private void findViewsById() {
        editTextStart = (EditText) findViewById(R.id.edit_text_start);
        editTextStart.setInputType(InputType.TYPE_NULL);
        editTextStart.requestFocus();

        editTextEnd = (EditText) findViewById(R.id.edit_text_end);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_item:
                // save the term
                Toast.makeText(this, "Save selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                // cancel term create
                Toast.makeText(this, "Cancel selected", Toast.LENGTH_SHORT).show();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        if(view == editTextStart) {
            startDatePickerDialog.show();
        } else if(view == editTextEnd) {
            endDatePickerDialog.show();
        }
    }
    private void setDefaultDates(){

        Calendar cal = Calendar.getInstance();

        Date startDate = Date.from(Instant.now());
        cal.setTime(startDate);
        cal.add(Calendar.DATE, 3);
        Date endDate = cal.getTime();// add 3 days to default end date

        editTextStart.setText(dateFormatter.format(startDate));
        editTextEnd.setText(dateFormatter.format(endDate));

        editTextStart.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                startDatePickerDialog.show();

        });

        editTextEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                endDatePickerDialog.show();

        });
    }
}