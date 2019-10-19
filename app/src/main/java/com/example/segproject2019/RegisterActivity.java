package com.example.segproject2019;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;

public class RegisterActivity extends AppCompatActivity {

    private EditText mTextUsername;
    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonRegister;
    private TextView mTextViewLogin;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    String username, password, userType;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = firebaseAuth.getInstance();

        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.TextViewLogin);
        radioGroup = findViewById(R.id.radioGroup);


        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, login_Activity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    final String userType = getUserType();
                    final String userName = mTextUsername.getText().toString();
                    final String userEmail = mTextEmail.getText().toString();
                    final String userPassword = mTextPassword.getText().toString();
                    final String passwordHashed = hashSHA256(userPassword);


                    firebaseAuth.createUserWithEmailAndPassword(userEmail, passwordHashed)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        User userInfo = new User(userName,userEmail, passwordHashed,
                                                userType);

                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(RegisterActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, login_Activity.class));
                                            }
                                        });


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Registration Failed invalid data", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }

        });




    }


    public void checkUserType(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        if (radioButton == findViewById(R.id.radio_admin)) {
            setUserType("Admin");
        } else if (radioButton == findViewById(R.id.radio_employe)) {
            setUserType("Employee");
        } else {
            setUserType("Patient");
        }
    }

    private Boolean validate() {
        Boolean result = false;

        username = mTextUsername.getText().toString();
        password = mTextPassword.getText().toString();


        if (username.isEmpty() || password.isEmpty() || radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static String hashSHA256(String myPassword){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(myPassword.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
