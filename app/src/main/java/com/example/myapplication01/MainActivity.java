package com.example.myapplication01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private MaterialButton signinButton;
    private ImageView goBack;
    private TextView signUpForNow,forgetPassword;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Your XML layout

        // Initialize the views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signinButton = findViewById(R.id.signInButton);
        goBack = findViewById(R.id.goBack);
        signUpForNow = findViewById(R.id.signUpForNow);
        forgetPassword = findViewById(R.id.forgetPassword);

        goBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, LandingPage.class));
            }
        });

        signUpForNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, LandingPage.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "https://www.netflix.com/in/loginhelp?netflixsource=android&fromApp=true";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        // Set the OnClickListener for the login button
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login logic here (e.g., check if email and password are valid)
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                    startActivity(new Intent(MainActivity.this, HomePage.class));
            }
        });

    }
}
