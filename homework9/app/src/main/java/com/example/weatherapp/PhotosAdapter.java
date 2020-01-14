package com.example.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder> {
    private Context mContext;
    private ArrayList<String> photosLinkList;

    public static class PhotosViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
//            mTextView1 = itemView.findViewById(R.id.textView);
        }
    }

    public PhotosAdapter(Context context, ArrayList<String> photosList) {
        mContext = context;
        photosLinkList = photosList;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.example_image_item, viewGroup, false);
        PhotosViewHolder pvh = new PhotosViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder photosViewHolder, int i) {
        String currentPhotoLink = photosLinkList.get(i);

//        photosViewHolder.mImageView.setImageResource(currentPhotoLink)

//        photosViewHolder.mTextView1.setText(currentPhotoLink);
        Picasso.with(mContext).load(currentPhotoLink)
                .fit()
                .centerCrop()
                .into(photosViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return photosLinkList.size();
    }
}
