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
import com.raselraihande.tutortrackbd.Model.TutorResponse;
import com.raselraihande.tutortrackbd.Model.TutorResponse.TutorPostModel;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.Views.Activity.TutorActivity;

import java.util.ArrayList;
import java.util.List;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private List<TutorPostModel> tutorList;
    private final List<TutorPostModel> tutorListFull;

    public TutorAdapter(List<TutorPostModel> tutorList) {
        this.tutorList = tutorList;
        this.tutorListFull = new ArrayList<>(tutorList);  // Keep a copy of the full list for filtering
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor, parent, false);
        return new TutorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        TutorPostModel tutor = tutorList.get(position);

        holder.txtTeacherNam.setText(tutor.getName());
        holder.txtTeacherLoc.setText(tutor.getLocation());
        holder.txtTechercls.setText(tutor.getStudyAt());
        holder.txtTeacherSub.setText(tutor.getSubject());

        Glide
                .with(holder.imgTecherPro.getContext())
                .load("https://whitemag.xyz/TutorTrack/Tutor/" + tutor.getImageBase64())
                .circleCrop()
                .error(R.drawable.icteacher)
                .placeholder(R.drawable.icteacher)
                .into(holder.imgTecherPro);

        holder.layTutor.setOnClickListener(view -> {

            Intent intent = new Intent(holder.layTutor.getContext(), TutorActivity.class);
            intent.putExtra("name", tutor.getName());
            intent.putExtra("details", tutor.getDetails());
            intent.putExtra("studyAt", tutor.getStudyAt());
            intent.putExtra("location", tutor.getLocation());
            intent.putExtra("studentClass", tutor.getStudentClass());
            intent.putExtra("weekDay", tutor.getWeekDay());
            intent.putExtra("medium", tutor.getMedium());
            intent.putExtra("subject", tutor.getSubject());
            intent.putExtra("salary", tutor.getSalary());
            intent.putExtra("number", tutor.getNumber());
            intent.putExtra("imageBase64", "https://whitemag.xyz/TutorTrack/Tutor/" + tutor.getImageBase64());
            intent.putExtra("postdate", tutor.getPostdate());
            holder.layTutor.getContext().startActivity(intent);

        });

    }
    @Override
    public int getItemCount() {
        return tutorList != null ? tutorList.size() : 0;
    }

    public static class TutorViewHolder extends RecyclerView.ViewHolder {
        TextView txtTeacherNam, txtTeacherLoc, txtTechercls, txtTeacherSub;
        ImageView imgTecherPro;
        LinearLayout layTutor;

        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTeacherNam = itemView.findViewById(R.id.txtTeacherNam);
            txtTeacherLoc = itemView.findViewById(R.id.txtTeacherLoc);
            imgTecherPro = itemView.findViewById(R.id.imgTecherPro);
            txtTechercls = itemView.findViewById(R.id.txtTechercls);
            txtTeacherSub = itemView.findViewById(R.id.txtTeacherSub);
            layTutor = itemView.findViewById(R.id.layTutor);
        }
    }

    // Method to filter the list
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            tutorList = new ArrayList<>(tutorListFull);  // Reset to full list if no query
        } else {
            List<TutorPostModel> filteredList = new ArrayList<>();
            String lowerCaseQuery = query.toLowerCase();
            for (TutorPostModel tutor : tutorListFull) {
                if (tutor.getSearchlist().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(tutor);
                }
            }
            tutorList = filteredList;
        }
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateTutorList(List<TutorResponse.TutorPostModel> newTutorList) {
        tutorList.clear();
        tutorList.addAll(newTutorList);
        tutorListFull.clear();
        tutorListFull.addAll(newTutorList);  // Update the full list as well
        notifyDataSetChanged();
    }

}
