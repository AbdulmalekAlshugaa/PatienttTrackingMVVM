package com.example.osamah.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osamah.Fragments.OnclickList;
import com.example.osamah.R;
import com.example.osamah.model.Notifications;
import com.example.osamah.model.User;

import java.util.ArrayList;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder>   {
    private Context mCtxt;
    private ArrayList<Notifications> seisureModelArrayList;


    // create a const


    public CommentsListAdapter(Context mCtxt, ArrayList<Notifications> userArrayList) {
        this.mCtxt = mCtxt;
        this.seisureModelArrayList = userArrayList;
    }

    @NonNull
    @Override
    public CommentsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comments, parent, false);
        CommentsListAdapter.ViewHolder holder = new CommentsListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Plus.setText(seisureModelArrayList.get(position).getComments());
        holder.Name.setText(seisureModelArrayList.get(position).getDrEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mCtxt, OnclickList.class);
//                intent.putExtra("name",seisureModelArrayList.get(position).getFullName());
//                intent.putExtra("email", seisureModelArrayList.get(position).getEmail());
//                intent.putExtra("uid", seisureModelArrayList.get(position).getUid());
//                mCtxt.startActivity(intent);



            }
        });

    }



    @Override
    public int getItemCount() {
        return seisureModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private TextView Plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.drNAmes);
            Plus = itemView.findViewById(R.id.comments);


        }

    }
}
