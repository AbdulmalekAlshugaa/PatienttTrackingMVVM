package com.example.osamah.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osamah.Fragments.OnclickList;
import com.example.osamah.R;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>   {
    private Context mCtxt;
    private ArrayList<User> seisureModelArrayList;


    // create a const


    public UserListAdapter(Context mCtxt, ArrayList<User> userArrayList) {
        this.mCtxt = mCtxt;
        this.seisureModelArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        UserListAdapter.ViewHolder holder = new UserListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(seisureModelArrayList.get(position).getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtxt, OnclickList.class);
                intent.putExtra("name",seisureModelArrayList.get(position).getFullName());
                intent.putExtra("email", seisureModelArrayList.get(position).getEmail());
                mCtxt.startActivity(intent);


            }
        });

    }



    @Override
    public int getItemCount() {
        return seisureModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private ImageView Plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Plus = itemView.findViewById(R.id.plus);


        }

    }
}
