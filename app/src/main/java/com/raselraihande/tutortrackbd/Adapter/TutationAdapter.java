package com.raselraihande.tutortrackbd.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raselraihande.tutortrackbd.Model.TuationResponse.TuationPostModel;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.Views.Activity.TuationActivity;

import java.util.ArrayList;
import java.util.List;

public class TutationAdapter extends RecyclerView.Adapter<TutationAdapter.TutationViewHolder> {

    private List<TuationPostModel> tuationList;
    private final List<TuationPostModel> tuationListFull;

    // Constructor
    public TutationAdapter(List<TuationPostModel> tuationList) {
        this.tuationList = tuationList;
        this.tuationListFull = new ArrayList<>(tuationList); // Keep a copy of the full list for filtering
    }

    @NonNull
    @Override
    public TutationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuation, parent, false);
        return new TutationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TutationViewHolder holder, int position) {
        TuationPostModel tuation = tuationList.get(position);

        holder.txtTutionName.setText(tuation.getName());
        holder.txtClass.setText(tuation.getStudentClass());
        holder.txtSubject.setText(tuation.getSubject());
        holder.txtLocation.setText(tuation.getLocation());
        holder.txtSalary.setText(tuation.getSalary());

        // Load image using Glide
        Glide
                .with(holder.imgTuation.getContext())
                .load("https://whitemag.xyz/TutorTrack/Tuation/" + tuation.getImageBase64())
                .placeholder(R.drawable.icman)
                .circleCrop()
                .error(R.drawable.icman)
                .into(holder.imgTuation);

        holder.layTuation.setOnClickListener(view -> {
            Intent intent = new Intent(holder.layTuation.getContext(), TuationActivity.class);

            intent.putExtra("name", tuation.getName());
            intent.putExtra("details", tuation.getDetails());
            intent.putExtra("studyAt", tuation.getStudyAt());
            intent.putExtra("location", tuation.getLocation());
            intent.putExtra("studentClass", tuation.getStudentClass());
            intent.putExtra("weekDay", tuation.getWeekDay());
            intent.putExtra("medium", tuation.getMedium());
            intent.putExtra("subject", tuation.getSubject());
            intent.putExtra("studentsNo", tuation.getStudentsNo());
            intent.putExtra("salary", tuation.getSalary());
            intent.putExtra("studyTime", tuation.getStudyTime());
            intent.putExtra("number", tuation.getNumber());
            intent.putExtra("imageBase64","https://whitemag.xyz/TutorTrack/Tuation/" + tuation.getImageBase64());
            intent.putExtra("postdate", tuation.getPostdate());
            holder.layTuation.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tuationList != null ? tuationList.size() : 0;
    }

    // ViewHolder Class
    public static class TutationViewHolder extends RecyclerView.ViewHolder {
        TextView txtTutionName, txtClass, txtSubject, txtLocation, txtSalary;
        ImageView imgTuation;
        LinearLayout layTuation;

        public TutationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTutionName = itemView.findViewById(R.id.txttutionname);
            txtClass = itemView.findViewById(R.id.txtClaas);
            txtSubject = itemView.findViewById(R.id.txSubject);
            txtLocation = itemView.findViewById(R.id.txtuLocation);
            txtSalary = itemView.findViewById(R.id.txtSalery);
            imgTuation = itemView.findViewById(R.id.imgTution);
            layTuation = itemView.findViewById(R.id.layTuation);
        }
    }

    // Method to filter the list
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            tuationList = new ArrayList<>(tuationListFull); // Reset to full list if no query
        } else {
            List<TuationPostModel> filteredList = new ArrayList<>();
            String lowerCaseQuery = query.toLowerCase();
            for (TuationPostModel tuation : tuationListFull) {
                if (tuation.getSearchlist().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(tuation);
                }
            }
            tuationList = filteredList;
        }
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTuationList(List<TuationPostModel> newTuationList) {
        tuationList.clear();
        tuationList.addAll(newTuationList);
        tuationListFull.clear();
        tuationListFull.addAll(newTuationList); // Update the full list as well
        notifyDataSetChanged();
    }
}

