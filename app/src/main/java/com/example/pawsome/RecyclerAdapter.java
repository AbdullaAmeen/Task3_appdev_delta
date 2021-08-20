package com.example.pawsome;

import android.app.SearchManager;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Dogs> dogsArrayList;
    private Context context;
    OnCardListener onCardListener;

    public RecyclerAdapter(Context context, ArrayList<Dogs> dogsArrayList, OnCardListener onCardListener) {
        this.dogsArrayList = dogsArrayList;
        this.context = context;
        this.onCardListener = onCardListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onCardListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Dogs dog = dogsArrayList.get(position);
        if(!dog.getBreeds().isEmpty())
            holder.tv_name.setText(dog.getBreeds().get(0).getName());
        Picasso.get().load(dog.getUrl()).into(holder.im_dogs);


    }

    @Override
    public int getItemCount() {
        if(!dogsArrayList.isEmpty())
            return dogsArrayList.size();

            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnCardListener onCardListener;
        ConstraintLayout lt_parent;
        TextView tv_name;
        ImageView im_dogs;
        public ViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            lt_parent = itemView.findViewById(R.id.lt_parent);
            im_dogs = itemView.findViewById(R.id.im_dogs);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }


}
