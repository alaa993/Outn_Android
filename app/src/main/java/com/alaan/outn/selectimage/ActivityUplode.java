package com.alaan.outn.selectimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.alaan.outn.R;
import com.alaan.outn.utils.UtilsJava;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityUplode extends AppCompatActivity implements ChangeData, EntkabhImag {
    private ImageAdapter galleryAdapter;
    List<MovieAddImag> movieAddImag = new ArrayList<>();
    private RecyclerView recycler_view;
    private boolean isfirst = false;
    List<String> urlImge = new ArrayList<>();
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 3;
    private Uri mImageCaptureUri;
    File file;
    private static final int PICK_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplode);

        setRecyclerView();
    }


    private void setRecyclerView() {
        recycler_view = findViewById(R.id.recycler_view);
        if (!isfirst) {
            MovieAddImag movieAddImag1 = new MovieAddImag();
            movieAddImag1.setImageIcon(R.drawable.camera);
            movieAddImag.add(movieAddImag1);
            isfirst = true;
        }
        galleryAdapter = new ImageAdapter(ActivityUplode.this, movieAddImag);
        @SuppressLint("WrongConstant")
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivityUplode.this, 3, GridLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(gridLayoutManager);
        recycler_view.setAdapter(galleryAdapter);
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(ActivityUplode.this, recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if (position == 0) {


                    if (movieAddImag.size() >= 4) {
                        Toast.makeText(ActivityUplode.this, "بیشتر از سه عکس نمی توانید انتخاب کنید", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogEntkabeImag dialogEntkabeImag = new DialogEntkabeImag(ActivityUplode.this, ActivityUplode.this, false);
                        dialogEntkabeImag.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        dialogEntkabeImag.show();
                    }

                } else {
                    DialogDelate dialogDelate = new DialogDelate(ActivityUplode.this, new ChangeData() {
                        @Override
                        public void onSuccse() {
                        }

                        @Override
                        public void onDalate() {
                            delate(position);
                        }
                    });

                    dialogDelate.show();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onSuccse() {

    }

    @Override
    public void onDalate() {

    }


    public void delate(final int position) {
        boolean status = false;
        String uri = movieAddImag.get(position).getUrl();
        Iterator Camra = urlImge.iterator();
        while (Camra.hasNext()) {
            String x = (String) Camra.next();
            if (x.equals(uri)) {
                Camra.remove();
                status = true;
            }
        }

        if (status) {
            movieAddImag.remove(position);
        }
        recycler_view.post(new Runnable() {
            @Override
            public void run() {
                galleryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void entkabhImagCamra() {
       // camera();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    @Override
    public void entkabhImagGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_GALLERY);
    }

    @Override
    public void deleteImag() {

    }

    String imagePath;
    Bitmap bitmap = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_FILE:
                    String path = UtilsJava.getPath(data.getData());

                    if (path != null) {
                        File file = new File(path);
                        Uri uri = Uri.fromFile(file);
                        startCropImageActivity(uri);
                    }
                    break;
                case PICK_FROM_CAMERA:
                    //  G.log("mImageCaptureUri", "mImageCaptureUri : " + mImageCaptureUri);
                    startCropImageActivity(mImageCaptureUri);

                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    final Uri resultUri = result.getUri();
                    // G.log("image", "url : " + resultUri.getPath());
                    imagePath = resultUri.getPath();
                    MovieAddImag movieAddImag1 = new MovieAddImag();
                    movieAddImag1.setUrl(imagePath);
                    movieAddImag.add(movieAddImag1);
                    galleryAdapter.notifyDataSetChanged();
                    urlImge.add(imagePath);
                    if (imagePath != null) {
                        if (!imagePath.equals("")) {
                            bitmap = BitmapFactory.decodeFile(imagePath);
                            //SaveImage(bitmap);
                        }
                    }

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }


    private void startCropImageActivity(Uri mImageCaptureUri) {
        CropImage.activity(mImageCaptureUri)
                .setAutoZoomEnabled(true)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(600, 600)
                .setOutputCompressQuality(60)
                .start(this);
    }
}