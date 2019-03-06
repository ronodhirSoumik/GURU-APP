package com.example.hp.myapplication.app;

import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class searchAdapter extends RecyclerView.Adapter <searchAdapter.tutorViewHolder>{
    static Context context;
    static ArrayList<String> IDTutors= new ArrayList<String>();
    ArrayList<String> nameTutors= new ArrayList<String>();
    ArrayList<String> phoneTutors = new ArrayList<String>();

    public static class tutorViewHolder extends RecyclerView.ViewHolder {
        TextView layoutName, layoutPhone;

        public tutorViewHolder(@NonNull View itemView) {
            super(itemView);

             layoutName = itemView.findViewById(R.id.layoutName);
             layoutPhone = itemView.findViewById(R.id.layoutPhone);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //Toast.makeText(context, "Detail "+ IDTutors.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, checkTutorProfile.class);
                    intent.putExtra("ID",IDTutors.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }

    public searchAdapter(Context context, ArrayList<String> nameTutors, ArrayList<String> phoneTutors, ArrayList<String> IDTutors) {
        this.context = context;
        this.nameTutors = nameTutors;
        this.phoneTutors = phoneTutors;
        this.IDTutors = IDTutors;
    }

    @NonNull
    @Override
    public searchAdapter.tutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list, parent, false);
        return new searchAdapter.tutorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorViewHolder holder, int position) {
        holder.layoutName.setText(nameTutors.get(position));
        holder.layoutPhone.setText(phoneTutors.get(position));

    }


    @Override
    public int getItemCount() {
        return nameTutors.size();
    }
}


