package com.example.tez.Activitys.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tez.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Faruk_ALTAY  05.05.2019
 */


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    Button btnGiris;
    Button btnKayitOl;
    private EditText edtMail;
    private EditText edtSifre;
    private String userName;
    private String userPassword;
    ProgressDialog progressDialog;
    SignInButton signInButton;
    GoogleApiClient mGoogleApiClient;
    String TAG="HATALAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGiris =(Button)findViewById(R.id.btnGiris);
        btnKayitOl =(Button)findViewById(R.id.btnKayitOl);
        edtMail =(EditText)findViewById(R.id.editTextEmail);
        edtSifre =(EditText)findViewById(R.id.editTextSifre);




        //FirebaseAuth sınıfının referans olduğu nesneleri kullanabilmek için getInstance methodunu kullanıyoruz.
        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser(); // authenticated user

        //Geçerli bir yetkilendirme olup olmadığını kontrol ediyoruz.
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,MenuActivity.class));
        }


        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KayitActivity.class);
                startActivity(intent);
            }
        });

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userName = edtMail.getText().toString();
                userPassword = edtSifre.getText().toString();
                if(userName.isEmpty() || userPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                }else{

                    progressDialog  = ProgressDialog.show(MainActivity.this,"Doğrulama","Bilgiler Kontrol ediliyor...",true);
                    progressDialog.show();
                    loginFunc();
                }
            }
        });
    }

    private void loginFunc() {

        //Kullanıcı giriş kontrol
        mAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(MainActivity.this,MenuActivity.class);
                            i.putExtra("mail",userName);
                            startActivity(i);
                            progressDialog.dismiss();
                            finish();

                        }
                        else{
                            // hata
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Kullanıcı Adı veya Şifre Hatalı!", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }




}