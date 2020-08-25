package com.rudolphh.studentscheduler.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.CourseMainActivity;

import java.util.List;
import java.util.Objects;

public class TermMainActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Terms");

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        TermAdapter termAdapter = new TermAdapter();
        recyclerView.setAdapter(termAdapter);

        // get viewModel instance
        termViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TermViewModel.class);

        // set up viewModel with liveData
        termViewModel.getAllTerms().observe(this, termEntities -> {
            termAdapter.setTerms(termEntities);
            // update RecyclerView
            Toast.makeText(TermMainActivity.this, "welCome to Terms!", Toast.LENGTH_LONG).show();
        });

    }// end onCreate

    public void openCreateTerm(View view){
        Intent intent = new Intent(this, TermCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}