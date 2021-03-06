package com.ceylinco.ceylincocustomerapp.existingPolicy.accidents;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ceylinco.ceylincocustomerapp.R;
import com.ceylinco.ceylincocustomerapp.models.ImageDetails;
import com.ceylinco.ceylincocustomerapp.util.DetectNetwork;
import com.ceylinco.ceylincocustomerapp.util.JsonRequestManager;
import com.ceylinco.ceylincocustomerapp.util.Notifications;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prishan Maduka on 8/8/2016.
 */
public class ReportAccidentsActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<ImageDetails> imageDetailses = new ArrayList<>();
    private ArrayList<String> selectedImageList = new ArrayList<>();
    private static final int CAMERA_REQUEST = 2000;
    private static final String IMAGE_DIRECTORY_NAME = "ceylinco";
    private static String jobId;
    private Uri fileUri;
    private ImageGalleryRecycleAdapter imageGalleryRecycleAdapter;
    private String policyNumber;
    private int imageCount = 0;
    private int selectedCount = 0;
    private static String vehicleNumber;
    private final Notifications notifications = new Notifications();
    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private Context context;
    private int uploadPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_view);
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

        policyNumber = getIntent().getStringExtra("POLICY");
        vehicleNumber = getIntent().getStringExtra("VEHICLE");
        jobId = "VIP_IMG_"+policyNumber+"_"+vehicleNumber;

        context = ReportAccidentsActivity.this;
        DetectNetwork.setmContext(context);

        Button addNewPhoto = (Button) findViewById(R.id.addNewPhoto);
        Button uploadPhotos = (Button) findViewById(R.id.upload);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imgGallery);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        imageGalleryRecycleAdapter = new ImageGalleryRecycleAdapter(this,imageDetailses);
        recyclerView.setAdapter(imageGalleryRecycleAdapter);
        addNewPhoto.setOnClickListener(this);
        uploadPhotos.setOnClickListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(imageDetailses.get(position).isChecked()){
                    imageDetailses.get(position).setChecked(false);
                    selectedCount = selectedCount -1;
                }else{
                    if(selectedCount<5){
                        imageDetailses.get(position).setChecked(true);
                        selectedCount = selectedCount +1;
                    }else{
                        alertDialog = notifications.showGeneralDialog(context,getResources().getString(R.string.max_images_upload));
                        alertDialog.show();
                    }

                }
                imageGalleryRecycleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("TAG","ON LONG CLICK");
            }
        }));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addNewPhoto){
            if(imageCount<10){
                captureImage();
            }else{
                alertDialog = notifications.showGeneralDialog(context,getResources().getString(R.string.max_images_capturing));
                alertDialog.show();
            }

        }else if(v.getId()==R.id.upload){
            for(int i=0;i<imageDetailses.size();i++){
                if(imageDetailses.get(i).isChecked()){
                    selectedImageList.add(imageDetailses.get(i).getImagePath());
                }
            }
            if(selectedImageList.size()>0){
                progress = new ProgressDialog(context);
                progress.setMessage("Uploading 1 of " + selectedCount + " images");
                progress.show();
                progress.setCanceledOnTouchOutside(true);
                uploadImage(uploadPosition);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ImageDetails imageDetails = new ImageDetails();

            try {
                imageDetails.setImagePath(fileUri.getPath());
                imageDetails.setChecked(false);

                imageDetailses.add(imageDetails);
                imageGalleryRecycleAdapter.notifyDataSetChanged();

                imageCount = imageCount +1;

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri();

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile(1));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME+"/"+jobId);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" +vehicleNumber+"_"+ timeStamp + ".png");
        }else {
            return null;
        }

        return mediaFile;
    }

    //ClickListener interface for images recycle view
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private final GestureDetector gestureDetector;
        private final ReportAccidentsActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ReportAccidentsActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private void uploadImage(int position){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap photo = BitmapFactory.decodeFile(selectedImageList.get(position),options);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        String[] parts = selectedImageList.get(position).split("/");
        int size = parts.length;

        JsonRequestManager.getInstance(this).uploadImage(getResources().getString(R.string.image_upload_url),encoded,jobId, parts[size-1].replace("png","jpg"), callback);
    }

    /**
     * Callback to handle image upload
     */
    private final JsonRequestManager.uploadImageRequest callback = new JsonRequestManager.uploadImageRequest() {
        @Override
        public void onSuccess(String response) {
            if(progress!=null){
                progress.dismiss();
            }

            if((uploadPosition+1)<selectedCount){
                uploadPosition = uploadPosition +1;
                progress = new ProgressDialog(context);
                progress.setMessage("Uploading "+ (uploadPosition+1)+" of " + selectedCount + " images");
                progress.show();
                progress.setCanceledOnTouchOutside(true);
                uploadImage(uploadPosition);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Images uploaded successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

            String APPLICATION_TAG = "IMAGE UPLOAD";


        }

        @Override
        public void onError(String response) {
            if(progress!=null){
                progress.dismiss();
            }
            alertDialog = notifications.showGeneralDialog(context,response);
            alertDialog.show();
        }
    };
}
