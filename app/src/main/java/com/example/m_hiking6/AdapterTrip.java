package com.example.m_hiking6;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTrip extends RecyclerView.Adapter<AdapterTrip.MyViewHolder> {
    private Context context;

    Activity activity;
    private ArrayList trip_id, trip_name, trip_location, trip_date, trip_parking, trip_length, trip_difficult, trip_description;


    AdapterTrip(Activity activity,Context context, ArrayList trip_id, ArrayList trip_name, ArrayList trip_location, ArrayList trip_date,
                ArrayList trip_parking, ArrayList trip_length, ArrayList trip_difficult, ArrayList trip_description){
        this.activity = activity;
        this.context = context;
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.trip_location = trip_location;
        this.trip_date = trip_date;
        this.trip_parking = trip_parking;
        this.trip_length = trip_length;
        this.trip_difficult = trip_difficult;
        this.trip_description = trip_description;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_name_txt.setText(String.valueOf(trip_name.get(position)));
        holder.trip_location_txt.setText(String.valueOf(trip_location.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date.get(position)));
        holder.trip_parking_txt.setText(String.valueOf(trip_parking.get(position)));
        holder.trip_length_txt.setText(String.valueOf(trip_length.get(position)));
        holder.trip_difficult_txt.setText(String.valueOf(trip_difficult.get(position)));
        holder.trip_description_txt.setText(String.valueOf(trip_description.get(position)));

        //Onclick event to update
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), UpdateActivity.class);

                intent.putExtra("id", String.valueOf(trip_id.get(holder.getAdapterPosition())));
                intent.putExtra("name", String.valueOf(trip_name.get(holder.getAdapterPosition())));
                intent.putExtra("location", String.valueOf(trip_location.get(holder.getAdapterPosition())));
                intent.putExtra("date", String.valueOf(trip_date.get(holder.getAdapterPosition())));
                intent.putExtra("parking", String.valueOf(trip_parking.get(holder.getAdapterPosition())));
                intent.putExtra("length", String.valueOf(trip_length.get(holder.getAdapterPosition())));
                intent.putExtra("difficult", String.valueOf(trip_difficult.get(holder.getAdapterPosition())));
                intent.putExtra("description", String.valueOf(trip_description.get(holder.getAdapterPosition())));

                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {

        return trip_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, trip_name_txt, trip_location_txt, trip_date_txt, trip_parking_txt, trip_length_txt, trip_difficult_txt, trip_description_txt ;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_location_txt = itemView.findViewById(R.id.trip_location_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_parking_txt = itemView.findViewById(R.id.trip_parking_txt);
            trip_length_txt = itemView.findViewById(R.id.trip_length_txt);
            trip_difficult_txt = itemView.findViewById(R.id.trip_difficult_txt);
            trip_description_txt = itemView.findViewById(R.id.trip_description_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
