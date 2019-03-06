package com.example.hp.myapplication.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class overviewRecyclerAdapter extends RecyclerView.Adapter<overviewRecyclerAdapter.overviewViewHolder> {
     Context mcontext;
     List<itemOverview> mdata;
     Dialog dialog;



    public overviewRecyclerAdapter(Context mcontext, List<itemOverview> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
    }

    public static class overviewViewHolder extends RecyclerView.ViewHolder {

        private TextView t;
        private  TextView headTextView;
        private LinearLayout itemLinear;

        public overviewViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLinear = itemView.findViewById(R.id.itemLinear);
            t = itemView.findViewById(R.id.overViewtextView);
            headTextView = itemView.findViewById(R.id.headTextView);
        }


    }


    @Override
    public  overviewRecyclerAdapter.overviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(mcontext).inflate(R.layout.item_overview, parent, false);
        final overviewViewHolder overview = new overviewViewHolder(v);

        dialog  = new Dialog(mcontext);
        dialog.setContentView(R.layout.editdialogebox);

        overview.itemLinear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(mcontext, String.valueOf(overview.getAdapterPosition()), Toast.LENGTH_LONG).show();
                dialog.show();
            }
        });

        return overview;
    }

    @Override
    public void onBindViewHolder(@NonNull overviewRecyclerAdapter.overviewViewHolder holder, int position) {
        Log.d("IN ADAPTER ", mdata.get(position).getText());
        holder.t.setText(mdata.get(position).getText());
        holder.headTextView.setText(mdata.get(position).getHead());



    }

    @Override
    public int getItemCount() {
        return mdata.size() ;
    }

}
