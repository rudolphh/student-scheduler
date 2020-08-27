package com.rudolphh.studentscheduler.course.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
    private String termTitle;
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

        int numAssessments = currentCourseDetails.assessments.size();
        String numberOfAssessments =  (numAssessments == 1) ? numAssessments + " assessment" : numAssessments + " assessments";

        SpannableString content = new SpannableString( numberOfAssessments) ;
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 ) ;

        holder.textViewNumberAssessments.setText(content);

        // when user clicks on an individual course cardview
        holder.courseView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("courseId", currentCourseDetails.course.getId());
            bundle.putString("termTitle", termTitle);

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

    public void setTermTitle(String termTitle){
        this.termTitle = termTitle;
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

            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewStart = itemView.findViewById(R.id.text_view_course_start);
            textViewEnd = itemView.findViewById(R.id.text_view_course_end);
            textViewNumberAssessments = itemView.findViewById(R.id.text_view_number_assessments);
        }
    }
}
