package com.rudolphh.studentscheduler.course.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.main.AssessmentMainActivity;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.course.details.CourseDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseMainAdapter extends RecyclerView.Adapter<CourseMainAdapter.CourseHolder> {

    private List<CourseWithMentorAndAssessments> coursesDetails = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);

        return new CourseHolder(itemView);
    }

    /*******************  onBindViewHolder */
    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

        CourseWithMentorAndAssessments currentCourseDetails = coursesDetails.get(position);

        holder.textViewTitle.setText(currentCourseDetails.course.getTitle());// set title

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewStart.setText(formatter.format(currentCourseDetails.course.getStart()));
        holder.textViewEnd.setText(formatter.format(currentCourseDetails.course.getAnticipatedEnd()));

        String numberOfAssessments = currentCourseDetails.assessments.size() + " assessments";
        holder.textViewNumberAssessments.setText(numberOfAssessments);

        // when user clicks on an individual course cardview
        holder.courseView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("courseId", currentCourseDetails.course.getId());
            bundle.putString("courseTitle", currentCourseDetails.course.getTitle());

            Intent intent = new Intent(context, CourseDetailsActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        // when user taps on the number of assessments in the course
        holder.textViewNumberAssessments.setOnClickListener((view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("courseId", currentCourseDetails.course.getId());
            bundle.putString("courseTitle", currentCourseDetails.course.getTitle());
            bundle.putString("courseNotes", currentCourseDetails.course.getNotes());

            Intent intent = new Intent(context, AssessmentMainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return coursesDetails.size();
    }

    public void setCourses(List<CourseWithMentorAndAssessments> coursesDetails){
        this.coursesDetails = coursesDetails;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// CourseHolder
    static class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberAssessments;

        private View courseView;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            courseView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
            textViewNumberAssessments = itemView.findViewById(R.id.text_view_number_assessments);
        }
    }
}
