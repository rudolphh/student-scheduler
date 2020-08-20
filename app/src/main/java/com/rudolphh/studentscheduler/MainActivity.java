package com.rudolphh.studentscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.rudolphh.studentscheduler.term.TermAdapter;
import com.rudolphh.studentscheduler.term.TermEntity;
import com.rudolphh.studentscheduler.term.TermViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                //Toast.makeText(MainActivity.this, "OnChanged yo!", Toast.LENGTH_LONG).show();
            }
        });
    }
}