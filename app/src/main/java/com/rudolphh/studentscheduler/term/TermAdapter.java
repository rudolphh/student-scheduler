package com.rudolphh.studentscheduler.term;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.MainActivity;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.CourseMainActivity;
import com.rudolphh.studentscheduler.course.CourseRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    private List<TermEntity> terms = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {

        TermEntity currentTerm = terms.get(position);

        holder.textViewTitle.setText(currentTerm.getTitle());

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewStart.setText(formatter.format(currentTerm.getStart()));
        holder.textViewEnd.setText(formatter.format(currentTerm.getEnd()));

        //holder.textViewNumberCourses.setText(numCourses);

        holder.textViewNumberCourses.setOnClickListener((view -> {
            Bundle bundle = new Bundle();
            bundle.putString("goto", "TermMainActivity");

            Intent intent = new Intent(context, CourseMainActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermEntity> terms){
        this.terms = terms;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }



    ///////////////////////// TermHolder
    static class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberCourses;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
            textViewNumberCourses = itemView.findViewById(R.id.text_view_number_courses);
        }
    }
}
