package com.rudolphh.studentscheduler.assessment.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.rudolphh.studentscheduler.assessment.create.AssessmentCreateActivity;
import com.rudolphh.studentscheduler.assessment.database.Assessment;
import com.rudolphh.studentscheduler.assessment.details.AssessmentDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

public class AssessmentMainAdapter extends RecyclerView.Adapter<AssessmentMainAdapter.AssessmentHolder> {

    private List<Assessment> assessments = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);

        return new AssessmentHolder(itemView);
    }

    /*******************  onBindViewHolder */
    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        Assessment currentAssessment = assessments.get(position);

        holder.textViewTitle.setText(currentAssessment.getTitle());// set title

        // format text and set due date
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewDue.setText(formatter.format(currentAssessment.getDueDate()));

        String assessmentType = currentAssessment.getAssessmentType().toString();
        assessmentType = assessmentType.substring(0,1) + assessmentType.substring(1).toLowerCase();
        holder.textViewAssessmentType.setText(assessmentType);

        // when user clicks on an individual course cardview

        Bundle bundle = new Bundle();
        bundle.putLong("id_assessment", currentAssessment.getId_assessment());
        bundle.putLong("id_course", currentAssessment.getId_fkcourse());

        holder.assessmentView.setOnClickListener(view -> {

            Intent intent = new Intent(context, AssessmentDetailsActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        holder.ivEditAssessmentButton.setOnClickListener(view -> {

            Intent intent = new Intent(context, AssessmentCreateActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        // when user clicks on due date notification icon
        holder.ivDueNotify.setOnClickListener(view->{
            Toast.makeText(context, "Assessment due date notification set", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, AlertBroadcastReceiver.class);
            int notificationId = Integer.parseInt( "100" + currentAssessment.getId_assessment());
            bundle.putInt("notification_id", notificationId);
            bundle.putString("assessment_title", currentAssessment.getTitle());
            intent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    notificationId, intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentAssessment.getDueDate().getTime(), pendingIntent);
        });

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<Assessment> assessments){
        this.assessments = assessments;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// CourseHolder
    static class AssessmentHolder extends RecyclerView.ViewHolder {
        private View assessmentView;
        private TextView textViewTitle;
        private TextView textViewDue;
        private TextView textViewAssessmentType;
        private ImageView ivEditAssessmentButton;

        private ImageView ivDueNotify;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            assessmentView = itemView;
            textViewTitle = itemView.findViewById(R.id.text_view_assessment_title);
            textViewDue = itemView.findViewById(R.id.text_view_assessment_due);
            textViewAssessmentType = itemView.findViewById(R.id.text_view_assessment_type);
            ivEditAssessmentButton = itemView.findViewById(R.id.edit_assessment_button);
            ivDueNotify = itemView.findViewById(R.id.iv_due_notify);
        }
    }
}
