package com.jb.mareu.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.jb.mareu.R;
import com.jb.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    List<Reunion> listeDeRencontre;
    Context context;
    List<Reunion> listeDeRencontreFull;

    public RecyclerViewAdapter(List<Reunion> listeDeRencontre, Context context) {
        this.listeDeRencontreFull = listeDeRencontre;
        this.context = context;
        this.listeDeRencontre = new ArrayList<>(listeDeRencontreFull);
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
        //FORMATTAGE DES DONNEES
        String lieu = listeDeRencontre.get(position).getLieuReunion();

        String heure = listeDeRencontre.get(position).getHeureReunion().toString();
        heure.substring(0, heure.length() - 2);
        heure = heure.replace(':', 'h');

        String sujet = listeDeRencontre.get(position).getSujetReunion();

        String emailParticipants = String.valueOf(listeDeRencontre.get(position).getListeParticipants());
        emailParticipants = emailParticipants.substring(1, emailParticipants.length() - 1);

        //AFFICHAGE DES DONNEES
        holder.infoReunion.setText(context.getString(R.string.form_title) + " " + lieu + " - " + heure + " - " + (sujet.length() > 10 ? sujet.substring(0, 7) + "..." : sujet));
        holder.emailReunion.setText(emailParticipants.length() > 35 ? emailParticipants.substring(0, 35) + "..." : emailParticipants);
        holder.imageViewColor.setImageResource(String.valueOf(listeDeRencontre.get(position).getCouleur()).equals("pearl") ? R.color.pearl : R.color.green);

        //SUPPRESSION D'UNE REUNION
        holder.deleteReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OUVRIR BOITE DE DIALOGUE DEMANDANT A L'UTILISATEUR DE CONFIRMER LA SUPPRESSION
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(R.string.delete_meeting_title)
                        .setMessage(R.string.delete_meeting_message)
                        .setPositiveButton(R.string.delete_meeting_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //SUPPRIMER REUNION
                                MainActivity.reunionService.supprimerReunion(listeDeRencontre.get(holder.getAdapterPosition()));
                                MainActivity.recyclerView.setAdapter(MainActivity.adapter);
                                //REINITIALISE L'ADAPTER
                                MainActivity.adapter = new RecyclerViewAdapter(MainActivity.reunionService.getListeDeRencontre(), context);
                                MainActivity.recyclerView.setAdapter(MainActivity.adapter);
                            }
                        })
                        .setNegativeButton(R.string.delete_meeting_negative_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        //MODIFICATION D'UNE REUNION
        holder.meetingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OUVRIR LA FORM_ACTIVITY EN LUI PASSANT UNE VALEUR QUI INDIQUE Q'ON VEUT MODIFIER LA REUNION
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("position", listeDeRencontre.indexOf(listeDeRencontre.get(holder.getPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listeDeRencontre.size();
    }

    @Override
    public Filter getFilter() {
        return reunionFilter;
    }

    private final Filter reunionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Reunion> listeReunionFiltree = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                listeReunionFiltree.addAll(listeDeRencontreFull);
            }
            else{
                //FILTRER REUNIONS
                String filterPattern = constraint.toString().toLowerCase().trim();
                listeReunionFiltree = MainActivity.reunionService.filtrerReunion(filterPattern);
            }

            FilterResults results = new FilterResults();
            results.values = listeReunionFiltree;
            results.count = listeReunionFiltree.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listeDeRencontre.clear();
            listeDeRencontre.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewColor;
        TextView infoReunion;
        TextView emailReunion;
        ImageButton deleteReunion;
        RelativeLayout meetingLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewColor = itemView.findViewById(R.id.design_main_recyclerview_imageview_color);
            infoReunion = itemView.findViewById(R.id.design_main_recyclerview_info_reunion);
            emailReunion = itemView.findViewById(R.id.design_main_recyclerview_email_reunion);
            deleteReunion = itemView.findViewById(R.id.design_main_recyclerview_delete_reunion);
            meetingLayout = itemView.findViewById(R.id.oneLineMeetingLayout);
        }
    }

}
