package com.example.shopquikr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText emailId, password;
    Button signIn;
    TextView signUp;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.signin);
        signUp=findViewById(R.id.signup);
authStateListener=new FirebaseAuth.AuthStateListener() {


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Toast.makeText(LoginActivity.this,"You are logged in!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }
        else
            Toast.makeText(LoginActivity.this,"Please login!",Toast.LENGTH_SHORT).show();
    }
};
signIn.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v){
        String email=emailId.getText().toString();
        String pwd=password.getText().toString();
        if(email.isEmpty()){
            emailId.setError("Please enter email id");
            emailId.requestFocus();
        }
        else if(pwd.isEmpty()){
            password.setError("Please enter email id");
            password.requestFocus();
        }
        else if(email.isEmpty() && pwd.isEmpty())
            Toast.makeText(LoginActivity.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
        else if(!(email.isEmpty() && pwd.isEmpty())) {
            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                        Toast.makeText(LoginActivity.this, "Login error, please login again!", Toast.LENGTH_SHORT).show();
                    else {
                        Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intToHome);

                    }
                }
            });
        }
        else{
            Toast.makeText(LoginActivity.this,"Error occured!",Toast.LENGTH_SHORT).show();
        }



    }
    });
signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intSignUp = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intSignUp);
    }
});

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
