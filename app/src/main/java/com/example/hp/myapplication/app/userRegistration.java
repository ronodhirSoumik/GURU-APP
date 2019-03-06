package com.example.hp.myapplication.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class userRegistration extends AppCompatActivity {
    private EditText nameTutor;
    private EditText mobileTutor;
    private EditText passTutor;
    private EditText conpassTutor;
    private Button btnRegister;
    private String userID;
    private String imageID;
    private String  choice;

    private RadioGroup radioGrp;
    private RadioButton radioBtn;

    static final String SHARED_PREF ="sp";
    static final String idTutor="id";

    static final String SHARED_USER ="su";
    static final String user="id";

    FirebaseDatabase database;
    DatabaseReference databaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        nameTutor = findViewById(R.id.nameText);
        mobileTutor = findViewById(R.id.mobileText);
        passTutor = findViewById(R.id.passText);
        conpassTutor = findViewById(R.id.conpassText);
        btnRegister = findViewById(R.id.btnRegister);
        radioGrp =findViewById(R.id.radioGrp);


        setStatusBarTransparent();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId =  radioGrp.getCheckedRadioButtonId();
                radioBtn = findViewById(radioId);
                choice = radioBtn.getText().toString();

                if(radioBtn.getText().equals("Tutor")){
                    databaseref = FirebaseDatabase.getInstance().getReference("Tutors");
                }

                else {
                    databaseref = FirebaseDatabase.getInstance().getReference("Guardians");
                }

                saveUser();
                checkall();
            }
        });
    }

    public void checkBtn(View v){
        int radioId =  radioGrp.getCheckedRadioButtonId();
         radioBtn = findViewById(radioId);
        Toast.makeText(this, "Your account type will be: ", Toast.LENGTH_SHORT).show(); }

    private void setStatusBarTransparent(){
        if(Build.VERSION.SDK_INT >=21){
            getWindow().getDecorView().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void checkall(){
        String name = nameTutor.getText().toString().trim();
        String pass = passTutor.getText().toString().trim();
        String cpass = conpassTutor.getText().toString().trim();
        String mobile = mobileTutor.getText().toString().trim();
        imageID = "not selected";

        if(pass.equals(cpass)){
            userID = databaseref.push().getKey();
            Tutors tutors = new Tutors(userID, name, pass , mobile,imageID);

            databaseref.child(userID).child("id").setValue(userID);
            databaseref.child(userID).child("name").setValue(name);
            databaseref.child(userID).child("mobile no").setValue(mobile);
            databaseref.child(userID).child("password").setValue(pass);

            databaseref.child(userID).child("gender").setValue(" ");
            databaseref.child(userID).child("place").setValue(" ");
            databaseref.child(userID).child("profession").setValue(" ");
            databaseref.child(userID).child("university").setValue(" ");
            databaseref.child(userID).child("department").setValue(" ");
            databaseref.child(userID).child("education medium").setValue(" ");
            databaseref.child(userID).child("interested subjects").setValue(" ");


            Toast.makeText(this, "Registration completed successfully", Toast.LENGTH_SHORT).show();

            saveID();

            if(choice.equals("Tutor")){
                startActivity(new Intent(userRegistration.this , ScrollingActivityforTutor.class));
                finish();  }
            else{
                startActivity(new Intent(userRegistration.this , firstGuardian.class));
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
