package com.ceylinco.ceylincocustomerapp.existingPolicy.accidents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.ImageDetails;

import java.util.ArrayList;

/**
 * Created by Prishan Maduka on 8/8/2016.
 */
public class ImageGalleryAdpater extends BaseAdapter{

    Context context;
    ArrayList<ImageDetails> imageList;


    public ImageGalleryAdpater(Context context, ArrayList<ImageDetails> list){
        this.context = context;
        this.imageList = list;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.image_list_row, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.imgView);
            CheckBox checkBox = (CheckBox)gridView.findViewById(R.id.checkImage);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 22;
            final Bitmap photo = BitmapFactory.decodeFile(imageList.get(position).getImagePath(),options);
            imageView.setImageBitmap(photo);

            checkBox.setChecked(imageList.get(position).isChecked());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
