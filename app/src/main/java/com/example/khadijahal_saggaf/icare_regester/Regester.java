package com.example.khadijahal_saggaf.icare_regester;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Regester extends AppCompatActivity implements View.OnClickListener{

    EditText editTextUsername , editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView Loin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);

        editTextUsername = (EditText)findViewById(R.id.editText4);
        editTextEmail = (EditText)findViewById(R.id.editText3);
        editTextPassword = (EditText)findViewById(R.id.editText5);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.LogIn_Text).setOnClickListener(this);

    }

    private void registerUser() {
        String username = editTextUsername.getText().toString().trim();
        String useremail = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Name is required");
            editTextUsername.requestFocus();
            return;
        }

        if (useremail.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            editTextEmail.setError("Please enter a valid email ");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(useremail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Regester.this,Home.class));
                } else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                registerUser();
                break;

            case R.id.LogIn_Text:
                startActivity(new Intent(this,Login.class));
               break;
        }

    }
}
