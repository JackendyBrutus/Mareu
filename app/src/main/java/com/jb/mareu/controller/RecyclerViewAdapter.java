package com.jb.mareu.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Reunion> listeDeRencontre;
    Context context;

    public RecyclerViewAdapter(List<Reunion> listeDeRencontre, Context context) {
        this.listeDeRencontre = listeDeRencontre;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_main_recyclerview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //TO DO - CONCATENATE MEETING SUBJECT + MEETING HOUR...
        holder.infoReunion.setText(listeDeRencontre.get(position).getSujetReunion());
        holder.emailReunion.setText(String.valueOf(listeDeRencontre.get(position).getListeParticipants()));
        holder.imageViewColor.setImageResource(R.color.green);
    }

    @Override
    public int getItemCount() {
        return listeDeRencontre.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewColor;
        TextView infoReunion;
        TextView emailReunion;
        ImageButton deleteReunion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewColor = itemView.findViewById(R.id.design_main_recyclerview_imageview_color);
            infoReunion = itemView.findViewById(R.id.design_main_recyclerview_info_reunion);
            emailReunion = itemView.findViewById(R.id.design_main_recyclerview_email_reunion);
            deleteReunion = itemView.findViewById(R.id.design_main_recyclerview_delete_reunion);
        }
    }
}
