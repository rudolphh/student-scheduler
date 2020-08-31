package com.rudolphh.studentscheduler.term.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;

import java.util.Objects;

public class TermMainActivity extends AppCompatActivity {

    private TermMainViewModel termMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_main);

        setToolbarAndNavigation();

        // set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // set up adapter
        TermMainAdapter termMainAdapter = new TermMainAdapter();
        recyclerView.setAdapter(termMainAdapter);

        // get viewModel instance
        termMainViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(TermMainViewModel.class);

        // set up viewModel with liveData
        termMainViewModel.getAllTerms().observe(this, termWithCourses -> {
            termMainAdapter.setTerms(termWithCourses);
            setToolBarTitles("Terms", "All");
            Toast.makeText(TermMainActivity.this, "welCome to Terms!", Toast.LENGTH_LONG).show();
        });

    }// end onCreate

    public void openCreateTerm(View view){
        Intent intent = new Intent(this, TermCreateActivity.class);
        startActivity(intent);
    }


    /////////////////// Navigation support
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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