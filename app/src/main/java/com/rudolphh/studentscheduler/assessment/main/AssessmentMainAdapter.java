package com.rudolphh.studentscheduler.assessment.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.assessment.database.AssessmentEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AssessmentMainAdapter extends RecyclerView.Adapter<AssessmentMainAdapter.AssessmentHolder> {

    private List<AssessmentEntity> assessments = new ArrayList<>();
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

        AssessmentEntity currentAssessment = assessments.get(position);

        holder.textViewTitle.setText(currentAssessment.getTitle());// set title

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewDue.setText(formatter.format(currentAssessment.getDueDate()));

        String assessmentType = currentAssessment.getAssessmentType().toString();
        assessmentType = assessmentType.substring(0,1) + assessmentType.substring(1).toLowerCase();
        holder.textViewAssessmentType.setText(assessmentType);

        // when user clicks on an individual course cardview
        holder.assessmentView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("assessmentId", currentAssessment.getId());
            bundle.putString("assessmentTitle", currentAssessment.getTitle());

//            Intent intent = new Intent(context, AssessmentDetailsActivity.class);
////            intent.putExtras(bundle);
////            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<AssessmentEntity> assessments){
        this.assessments = assessments;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// CourseHolder
    static class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDue;

        private TextView textViewAssessmentType;

        private View assessmentView;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);

            assessmentView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_assessment_title);
            textViewDue = itemView.findViewById(R.id.text_view_assessment_due);

            textViewAssessmentType = itemView.findViewById(R.id.text_view_assessment_type);
        }
    }
}
