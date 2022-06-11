package com.example.authentication.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authentication.R;
import com.example.authentication.database.AppDatabase;
import com.example.authentication.ui.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView tvInfo;
    private EditText etCorreo;
    private EditText etPass;
    private Button btnLogin;
    private Button btnRegister,btnLogOut;
    Context context ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AppDatabase.getInstance(this.getApplicationContext());

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        tvInfo = findViewById(R.id.tv_info);
        etCorreo = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_registrar);
        btnLogOut= findViewById(R.id.btn_logOut);

         context = getApplicationContext();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(etCorreo.getText().toString(),etPass.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAcount(etCorreo.getText().toString(),etPass.getText().toString());

            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user = firebaseAuth.getCurrentUser();

                 if (user != null){

                     Log.d("Main Activity", "logeado :" + user.getUid() );

                }else {
                     Log.d("Main Activity", "No logeado" );
                 }
            }
        };


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);

        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void createAcount(String correo, String pass) {
        mAuth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("MainActivity", "Usuario creado con email" + task.isSuccessful());
                getInfo();
            }
        });
    }


    private void login(String email, String pass){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Pasar a la pantalla de home
                    Log.d("MainActivity", "Usuario logeado con exito" + task.isSuccessful());
                   // getInfo();
                    goHome();

                }else {
                    Toast toast = Toast.makeText(context, "Error al iniciar sesion",  Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


    private  void logOut(){
        mAuth.signOut();
        Log.d("MainActivity", "Session cerrada" );

    }



    private void getInfo(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String name = user.getDisplayName();
        String uid = user.getUid();
      //  String photo = user.getPhotoUrl().toString();


        String info= "El correo es :"+ email + "\n"+ "El nombre es: "+ name + " \n " +"El UID es :"+ uid +"\n" + "la  url de la foto " ;
        tvInfo.setText(info);

    }


    private void goHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);


    }


}