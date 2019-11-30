package com.example.kanikanavbar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanikanavbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText telephone;
    private EditText type;
    private EditText description;

    private Button registerBtn;
    private TextView loginPage;
    private FirebaseAuth authentication;

    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authentication = FirebaseAuth.getInstance();

        name= (EditText) findViewById(R.id.names);
        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password = (EditText) findViewById(R.id.password);
        registerBtn = (Button) findViewById(R.id.register);
        loginPage = (TextView) findViewById(R.id.txt2);

        Button btn = (Button)findViewById(R.id.register);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        btn.startAnimation(animation);

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, com.example.kanikanavbar.View.LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String names = name.getText().toString();
                final String mail = email.getText().toString();
                String pass = password.getText().toString();
//                String pass2 = email.getText().toString();

//                if(names.getText().length()==0){
//                    names.setError("This field is required!");
//                    names.requestFocus();
//                }
                 if(email.getText().length()==0){
                    email.setError("This field is required!");
                    email.requestFocus();
                }
                else if(password.getText().length()==0){
                    password.setError("This field is required");
                    password.requestFocus();
                }
//                else if(confirmpassword.getText().length()==0){
//                    confirmpassword.setError("This field is required");
//                    confirmpassword.requestFocus();
//                }
                else if(email.getText().length()==0 && password.getText().length()==0 && confirmpassword.getText().length()==0 && names.getText().length()==0){
                    Toast.makeText(SignupActivity.this, "Fill all the fields please", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().length()!=0 && password.getText().length()!=0){

                    authentication.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Toast.makeText(SignupActivity.this, "Your account have been registered!", Toast.LENGTH_SHORT).show();
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Authentication failed! Please try again!", Toast.LENGTH_SHORT).show();
                            } else {
                                final FirebaseUser firebaseUser= authentication.getCurrentUser();
                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignupActivity.this, "check your mails", Toast.LENGTH_SHORT).show();
                                            authentication.getCurrentUser().reload();

                                            String personId = firebaseUser.getUid();


                                            dbRef= FirebaseDatabase.getInstance().getReference("Persons").child(personId);
                                            HashMap<String, String> hs= new HashMap<>();
                                            hs.put("id", personId);
                                            hs.put("name", names);
                                            hs.put("email", mail);
                                            hs.put("password", names);
                                            hs.put("name", names);

                                        }

                                    }
                                });
                                startActivity(new Intent(SignupActivity.this, com.example.kanikanavbar.View.LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }

            }
        });

    }
}
