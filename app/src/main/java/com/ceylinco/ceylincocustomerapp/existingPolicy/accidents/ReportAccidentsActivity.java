package com.ceylinco.ceylincocustomerapp.existingPolicy.accidents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.ImageDetails;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prishan Maduka on 8/8/2016.
 */
public class ReportAccidentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addNewPhoto,uploadPhotos;
    private GridView gridView;
    private ImageGalleryAdpater adapter;
    private ArrayList<ImageDetails> imageDetailses = new ArrayList<>();
    private static final int CAMERA_REQUEST = 2000;
    private static final String IMAGE_DIRECTORY_NAME = "ceylinco";
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_text, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.mytext);
        textviewTitle.setText(getResources().getString(R.string.report_accident));
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        initialize();
    }

    private void initialize() {
        addNewPhoto = (Button)findViewById(R.id.addNewPhoto);
        uploadPhotos = (Button)findViewById(R.id.upload);
        gridView = (GridView)findViewById(R.id.imgGallery);
        adapter = new ImageGalleryAdpater(this,imageDetailses);

        addNewPhoto.setOnClickListener(this);
        uploadPhotos.setOnClickListener(this);

        gridView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addNewPhoto){
            captureImage();
        }else if(v.getId()==R.id.upload){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ImageDetails imageDetails = new ImageDetails();

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                final Bitmap photo = BitmapFactory.decodeFile(fileUri.getPath(),
                        options);
                //pathList.add(fileUri.getPath());
                imageDetails.setImagePath(fileUri.getPath());
                imageDetails.setChecked(true);

                imageDetailses.add(imageDetails);
                adapter.notifyDataSetChanged();

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");
        } else if (type == 2) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
