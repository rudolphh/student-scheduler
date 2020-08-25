package com.rudolphh.studentscheduler.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.rudolphh.studentscheduler.R;

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
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> termEntities) {
                termAdapter.setTerms(termEntities);
                // update RecyclerView
                Toast.makeText(TermMainActivity.this, "welCome to Terms!", Toast.LENGTH_LONG).show();
            }
        });
    }// end onCreate


}