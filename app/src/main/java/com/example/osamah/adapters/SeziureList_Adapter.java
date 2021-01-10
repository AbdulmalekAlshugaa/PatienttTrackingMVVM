package com.example.osamah.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import my.com.troopers.parttime.android.R;
import my.com.troopers.parttime.android.model.Annoucements;

import static my.com.troopers.parttime.android.utilities.helper.Constants.announcementDateConverter;


public class SeziureList_Adapter extends RecyclerView.Adapter<Announcement_Adapter.ViewHolder> {
    private Context mCtxt;
    private ArrayList<Annoucements> mAnnoucementsArrayList;


    // create a const


    public SeziureList_Adapter(Context mCtxt, ArrayList<Annoucements> mAnnoucementsArrayList) {
        this.mCtxt = mCtxt;
        this.mAnnoucementsArrayList = mAnnoucementsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mDate.setText(announcementDateConverter(mAnnoucementsArrayList.get(position).getCreatedAt()));
        holder.mBody.setText(mAnnoucementsArrayList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return mAnnoucementsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.dateOfPost);
            mBody = itemView.findViewById(R.id.Body);


        }

    }
}




