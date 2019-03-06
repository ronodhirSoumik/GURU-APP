package com.example.hp.myapplication.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class tutorProfileSecond extends AppCompatActivity {
    private Button btnUpload;
    private String imageID;
    private ImageView upload_img;

    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_second);

        btnUpload = findViewById(R.id.btnUpload);
        upload_img = findViewById(R.id.upload_img);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });



    }

    private void uploadimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            imageUri =  data.getData();
            upload_img.setImageURI(imageUri);
        }
    }
    private String getFileExtensions(Uri uri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap m = MimeTypeMap.getSingleton();
        return m.getExtensionFromMimeType(cr.getType(uri));
    }
}
