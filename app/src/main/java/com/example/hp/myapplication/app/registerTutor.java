package com.example.hp.myapplication.app;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.widget.Toast.*;

public class registerTutor extends AppCompatActivity {
    private EditText nameTutor;
    private EditText mobileTutor;
    private EditText passTutor;
    private EditText conpassTutor;
    private Button btnRegister;
    private ImageView upload_img;
    private Button btnUpload;
    private String url;
    private String userID;
    private String imageID;
    private String  choice;

    private RadioGroup radioGrp;
    private RadioButton radioBtn;

    static final String SHARED_PREF ="sp";
    static final String idTutor="id";

    static final String SHARED_USER ="su";
    static final String user="id";

    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST=1;

    FirebaseDatabase database;
    DatabaseReference databaseref;
    StorageReference storageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);


        nameTutor = findViewById(R.id.nameText);
        mobileTutor = findViewById(R.id.mobileText);
        passTutor = findViewById(R.id.passText);
        conpassTutor = findViewById(R.id.conpassText);
        btnRegister = findViewById(R.id.btnRegister);
        upload_img = findViewById(R.id.upload_img);
        btnUpload = findViewById(R.id.btnUpload);

        radioGrp =findViewById(R.id.radioGrp);

    //    databaseref = FirebaseDatabase.getInstance().getReference("Tutors");
   //     storageref = FirebaseStorage.getInstance().getReference();

       btnUpload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uploadimage();
           }
       });

      btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int radioId =  radioGrp.getCheckedRadioButtonId();
              radioBtn = findViewById(radioId);
              choice = radioBtn.getText().toString();

              if(radioBtn.getText().equals("Tutor")){
                  databaseref = FirebaseDatabase.getInstance().getReference("Tutors");
                   storageref = FirebaseStorage.getInstance().getReference();
              }

              else {
                  databaseref = FirebaseDatabase.getInstance().getReference("Guardians");
                  storageref = FirebaseStorage.getInstance().getReference();
              }

              saveUser();
              checkall();
          }
      });
      }

    public void checkBtn(View v){
        int radioId =  radioGrp.getCheckedRadioButtonId();
        radioBtn = findViewById(radioId);
        Toast.makeText(this, "Your account type will be: "+ radioBtn.getText().toString(), Toast.LENGTH_LONG).show(); }


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

    private void checkall(){
        String name = nameTutor.getText().toString().trim();
        String pass = passTutor.getText().toString().trim();
        String cpass = conpassTutor.getText().toString().trim();
        String mobile = mobileTutor.getText().toString().trim();

       StorageReference filepath = storageref.child("photos").child(imageUri.getLastPathSegment());

        filepath.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     imageID = taskSnapshot.getDownloadUrl().toString();
                        databaseref.child(userID).child("image").setValue(imageID);
                    }
                });

          if(pass.equals(cpass)){
                userID = databaseref.push().getKey();
                Tutors tutors = new Tutors(userID, name, pass , mobile,imageID);

                databaseref.child(userID).child("id").setValue(userID);
                databaseref.child(userID).child("name").setValue(name);
                databaseref.child(userID).child("mobile no").setValue(mobile);
                databaseref.child(userID).child("password").setValue(pass);

              Toast.makeText(this, "Registration completed successfully", Toast.LENGTH_SHORT).show();

              saveID();

              if(choice.equals("Tutor")){
              startActivity(new Intent(registerTutor.this , ScrollingActivityforTutor.class));
              finish();  }
             else{
                  startActivity(new Intent(registerTutor.this , firstGuardian.class));
                  finish();  }
          }

          else {
              Toast.makeText(this,"Password is not same" , Toast.LENGTH_LONG).show();
              clearAll();
        } }

     private void clearAll(){
          nameTutor.setText("");
          passTutor.setText("");
          conpassTutor.setText("");
          mobileTutor.setText("");
          upload_img.setVisibility(View.INVISIBLE);
     }

     public void  saveID(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF , MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(idTutor , userID);
            editor.commit();
    }

    public void  saveUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_USER , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(user , choice);
        editor.commit();
    }

}

