package com.example.osamah.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osamah.R;
import com.example.osamah.model.SeisureModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SeziureList_Adapter extends RecyclerView.Adapter<SeziureList_Adapter.ViewHolder> {
    private Context mCtxt;
    private ArrayList<SeisureModel> seisureModelArrayList;


    // create a const


    public SeziureList_Adapter(Context mCtxt, ArrayList<SeisureModel> seisureModelArrayList) {
        this.mCtxt = mCtxt;
        this.seisureModelArrayList = seisureModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recordlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mCtxt).load(seisureModelArrayList.get(position).getNote()).into(holder.imageView);
        holder.mDate.setText(seisureModelArrayList.get(position).getDate());
        holder.mTime.setText(seisureModelArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return seisureModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mTime;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date);
            mTime = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image);


        }

    }
}




