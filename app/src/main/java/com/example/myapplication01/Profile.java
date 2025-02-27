package com.example.myapplication01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import java.io.File;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private ImageView profileImageView;
    private ImageView editPhotoIcon;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profileImageView);
        editPhotoIcon = findViewById(R.id.editPhotoIcon);

        // Click listener for the edit photo icon
        editPhotoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSourceDialog();
            }
        });
    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source")
                .setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Camera option
                            if (ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Profile.this,
                                        new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                            } else {
                                openCamera();
                            }
                        } else {
                            // Gallery option
                            openGallery();
                        }
                    }
                })
                .show();
    }

    private void openCamera() {
        // Create a file where the image will be stored
        File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_photo.jpg");
        imageUri = Uri.fromFile(photoFile);

        // Create an Intent to capture an image
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // Pass the URI to the camera intent
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                // Handle camera result: The image is saved to the file URI
                profileImageView.setImageURI(imageUri); // Display the captured image in ImageView
            } else if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                // Handle gallery result here
                Uri imageUriFromGallery = data.getData();
                profileImageView.setImageURI(imageUriFromGallery);
            }
        }
    }

    // Handle permission request result for CAMERA
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permission denied to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
