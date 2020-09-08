package com.rudolphh.studentscheduler.term.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;
import com.rudolphh.studentscheduler.term.create.TermCreateActivity;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;
import com.rudolphh.studentscheduler.term.details.TermDetailsActivity;
import com.rudolphh.studentscheduler.term.details.TermDetailsViewModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermMainAdapter extends RecyclerView.Adapter<TermMainAdapter.TermHolder> {

    private List<TermWithCourses> terms = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);

        return new TermHolder(itemView);
    }

    /*******************  onBindViewHolder */
    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {

        TermWithCourses currentTermDetails = terms.get(position);

        holder.textViewTitle.setText(currentTermDetails.term.getTitle());// set title

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewStart.setText(formatter.format(currentTermDetails.term.getStart()));
        holder.textViewEnd.setText(formatter.format(currentTermDetails.term.getEnd()));

        int numCourses = currentTermDetails.courses.size();

        String numberOfCourses =  (numCourses == 1) ? numCourses + " course" : numCourses + " courses";
        holder.textViewNumberCourses.setText(numberOfCourses);

        // when user clicks on term card to see term details (courses, etc.)
        holder.termView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id_term", currentTermDetails.term.getId_term());
            bundle.putString("termTitle", currentTermDetails.term.getTitle());

            // take us to CourseMain
            Intent intent = new Intent(context, TermDetailsActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        });

        holder.ivEditTermButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id_term", currentTermDetails.term.getId_term());

            Intent intent = new Intent(context, TermCreateActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermWithCourses> terms){
        this.terms = terms;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// TermHolder
    static class TermHolder extends RecyclerView.ViewHolder {

        private View termView;

        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberCourses;

        private ImageView ivEditTermButton;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            termView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
            textViewNumberCourses = itemView.findViewById(R.id.text_view_number_courses);
            ivEditTermButton = itemView.findViewById(R.id.edit_term_button);
        }
    }
}
