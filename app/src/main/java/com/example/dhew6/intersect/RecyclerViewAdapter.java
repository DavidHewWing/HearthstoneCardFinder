package com.example.dhew6.intersect;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<HearthstoneCard> cardsList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView typeTextView, nameTextView, classTextView;
        public ImageView cardImageView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            classTextView = itemView.findViewById(R.id.classTextView);
            cardImageView = itemView.findViewById(R.id.cardImageView);

        }
    }

    public RecyclerViewAdapter(List<HearthstoneCard> cardsList, Context context){

        this.cardsList = cardsList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HearthstoneCard card = cardsList.get(position);
        String cardName = card.getName();
        String typeName = card.getType();
        String className = card.getPlayerClass();

        holder.nameTextView.setText(cardName);
        holder.typeTextView.setText(typeName);
        holder.classTextView.setText(className);

        if(card.getImgURL() != null){
            Picasso.get().load(card.getImgURL()).into(holder.cardImageView);
        }
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
}
