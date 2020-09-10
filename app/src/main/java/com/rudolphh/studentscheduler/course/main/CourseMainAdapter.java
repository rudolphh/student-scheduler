package com.rudolphh.studentscheduler.course.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.AlertBroadcastReceiver;
import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.main.AssessmentMainActivity;
import com.rudolphh.studentscheduler.converters.StatusConverter;
import com.rudolphh.studentscheduler.course.create.CourseCreateActivity;
import com.rudolphh.studentscheduler.course.database.CourseWithMentorAndAssessments;
import com.rudolphh.studentscheduler.course.details.CourseDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

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

        int numAssessments = currentCourseDetails.assessments.size();
        String numberOfAssessments =  (numAssessments == 1) ? numAssessments + " assessment" : numAssessments + " assessments";

        SpannableString content = new SpannableString( numberOfAssessments) ;
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 ) ;

        holder.textViewNumberAssessments.setText(content);

        String courseStatus = StatusConverter.fromCourseStatus(currentCourseDetails.course.getCourseStatus());
        holder.textViewCourseStatus.setText(courseStatus);


        long id_course = currentCourseDetails.course.getId_course();
        Bundle bundle = new Bundle();
        bundle.putLong("id_course", id_course);
        bundle.putLong("id_term", currentCourseDetails.course.getId_fkterm());

        holder.ivEditButton.setOnClickListener(view -> {

            Intent intent = new Intent(context, CourseCreateActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        // when user clicks on an individual course cardview
        holder.courseView.setOnClickListener(view -> {

            Intent intent = new Intent(context, CourseDetailsActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        bundle.putString("course_title", currentCourseDetails.course.getTitle());

        // when user taps on the number of assessments in the course
        holder.textViewNumberAssessments.setOnClickListener((view -> {

            bundle.putString("courseNotes", currentCourseDetails.course.getNotes());

            Intent intent = new Intent(context, AssessmentMainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }));


        // when user clicks on start date notification icon
        holder.ivStartNotify.setOnClickListener(view->{
            Toast.makeText(context, "Course start notification set", Toast.LENGTH_SHORT).show();

            // create intent and set bundle of extras
            Intent intent = new Intent(context, AlertBroadcastReceiver.class);
            int notificationId = Integer.parseInt( "200" + id_course);
            bundle.putInt("notification_id", notificationId);
            bundle.putBoolean("course_start", true);
            intent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentCourseDetails.course.getStart().getTime(), pendingIntent);
        });

        // when user clicks on end date notification icon
        holder.ivEndNotify.setOnClickListener(view->{
            Toast.makeText(context, "Course end notification set", Toast.LENGTH_SHORT).show();

            // create intent and set bundle of extras
            Intent intent = new Intent(context, AlertBroadcastReceiver.class);
            int notificationId = Integer.parseInt( "300" + id_course);
            bundle.putInt("notification_id", notificationId);
            bundle.putBoolean("course_start", false);
            intent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentCourseDetails.course.getAnticipatedEnd().getTime(), pendingIntent);
        });
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

        private ImageView ivEditButton;
        private ImageView ivStartNotify;
        private ImageView ivEndNotify;

        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberAssessments;
        private TextView textViewCourseStatus;

        private View courseView;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            ivStartNotify = itemView.findViewById(R.id.iv_start_notify);
            ivEndNotify = itemView.findViewById(R.id.iv_end_notify);
            ivEditButton = itemView.findViewById(R.id.edit_button);
            courseView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewStart = itemView.findViewById(R.id.text_view_course_start);
            textViewEnd = itemView.findViewById(R.id.text_view_course_end);
            textViewNumberAssessments = itemView.findViewById(R.id.text_view_number_assessments);
            textViewCourseStatus = itemView.findViewById(R.id.course_status);
        }
    }
}
