package com.zeruk.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zeruk.instagramclonejava.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    String mailText;
    String passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        if(user!=null)
        {
            Intent intent = new Intent(MainActivity.this, feedActivitiy.class);
            startActivity(intent);
            finish();
        }


    }



    public void signInClicked(View view)
    {
        mailText = binding.mailText.getText().toString();
        passText = binding.passwordText.getText().toString();
        if(mailText.equals("")||passText.equals(""))
        {
            Toast.makeText(MainActivity.this, "Mail and password required!", Toast.LENGTH_LONG).show();
        }else {
            auth.signInWithEmailAndPassword(mailText, passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this, feedActivitiy.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void signUpClicked(View view)
    {
        mailText = binding.mailText.getText().toString();
        passText = binding.passwordText.getText().toString();
        if(mailText.equals("")||passText.equals(""))
        {
            Toast.makeText(MainActivity.this, "Mail and password required!", Toast.LENGTH_LONG).show();
        }else {
            auth.createUserWithEmailAndPassword(mailText, passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this, feedActivitiy.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }



}