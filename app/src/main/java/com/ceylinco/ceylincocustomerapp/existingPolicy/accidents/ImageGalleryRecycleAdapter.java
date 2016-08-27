package com.ceylinco.ceylincocustomerapp.existingPolicy.accidents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.ImageDetails;

import java.util.ArrayList;

/**
 * Created by Prishan Maduka on 8/9/2016.
 */
public class ImageGalleryRecycleAdapter extends  RecyclerView.Adapter<ImageGalleryRecycleAdapter.ImageViewHolder> {

    Context context;
    ArrayList<ImageDetails> imageList;

    public ImageGalleryRecycleAdapter(Context context, ArrayList<ImageDetails> list){
        this.context = context;
        this.imageList = list;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 22;
        final Bitmap photo = BitmapFactory.decodeFile(imageList.get(position).getImagePath(),options);
        holder.imageView.setImageBitmap(photo);
        if(imageList.get(position).isChecked())
            holder.checkBox.setImageResource(R.drawable.ic_checked);
        else
            holder.checkBox.setImageResource(R.drawable.ic_blank_check);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView checkBox;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imgView);
            checkBox = (ImageView)itemView.findViewById(R.id.checkImage);
        }
    }
}
