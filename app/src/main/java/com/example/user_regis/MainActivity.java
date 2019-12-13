package com.example.user_regis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button b1,b2;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        pd= new ProgressDialog(MainActivity.this);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=et1.getText().toString().trim();
                password=et2.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(MainActivity.this," Please enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(MainActivity.this," Please enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                pd.setMessage("Registrating User ....");
                pd.show();
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registration is successful ! ", Toast.LENGTH_SHORT).show();
                            Intent avi=new Intent(MainActivity.this,profile.class);
                            avi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(avi);
                            pd.dismiss();
                            //startActivity(new Intent (MainActivity.this,loginact.class));
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "You are already registered", Toast.LENGTH_SHORT).show();

                            }
                            else
                            { Toast.makeText(MainActivity.this, "Could not registration, please try again ! ", Toast.LENGTH_SHORT).show();

                        }
                            pd.dismiss();

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=et1.getText().toString().trim();
                password=et2.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Intent avi=new Intent(MainActivity.this,profile.class);
                            avi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(avi);
                            //FirebaseUser user = firebaseAuth.getCurrentUser();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials, please try again ! ", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }

                    }
                });

            }
        });
    }
});}@Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, profile.class));
        }
    }}


