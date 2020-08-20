package com.rudolphh.studentscheduler.term;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    private List<TermEntity> terms = new ArrayList<>();

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    static class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
        }
    }
}
