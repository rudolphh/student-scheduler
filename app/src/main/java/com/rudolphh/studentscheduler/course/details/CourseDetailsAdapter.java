package com.rudolphh.studentscheduler.course.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseDetailsAdapter extends RecyclerView.Adapter<CourseDetailsAdapter.CourseHolder> {

    private List<CourseWithMentorAndAssessments> courses = new ArrayList<>();
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

        CourseWithMentorAndAssessments courseDetails = courses.get(position);

        holder.textViewTitle.setText(courseDetails.course.getTitle());// set title

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewStart.setText(formatter.format(courseDetails.course.getStart()));
        holder.textViewEnd.setText(formatter.format(courseDetails.course.getAnticipatedEnd()));

        String numberOfAssessments = courseDetails.assessments.size() + " courses";
        holder.textViewNumberCourses.setText(numberOfAssessments);

        holder.courseView.setOnClickListener(view -> {

        });

/*        holder.textViewNumberCourses.setOnClickListener((view -> {
            Bundle bundle = new Bundle();
            bundle.putString("goto", "CourseMainActivity");
            bundle.putInt("courseId", courseDetails.course.getId());

            Intent intent = new Intent(context, CourseMainActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }));*/
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<CourseWithMentorAndAssessments> courses){
        this.courses = courses;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// CourseHolder
    static class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberCourses;

        private View courseView;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            courseView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
            textViewNumberCourses = itemView.findViewById(R.id.text_view_number_courses);
        }
    }
}
