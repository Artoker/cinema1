package com.example.wordlcinema;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUp extends AppCompatActivity {
    Button BtnSignUp, BtnAccount;
    EditText etMail, etPassword, etName, etSecondName, etPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etSecondName = findViewById(R.id.etSecondName);
        etPasswordAgain = findViewById(R.id.etPasswordAgain);
        BtnSignUp = findViewById(R.id.BtnSignUp);
        BtnAccount = findViewById(R.id.BtnAccount);

        BtnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etMail.getText().toString()) ||
                        TextUtils.isEmpty(etPassword.getText().toString()) ||
                        TextUtils.isEmpty(etSecondName.getText().toString()) ||
                        TextUtils.isEmpty(etName.getText().toString()) ||
                        TextUtils.isEmpty(etPasswordAgain.getText().toString()))
                {
                    ShowAlertDialogWindow("Есть пустые поля!");
                }
                else if (!etPassword.getText().toString().equals(etPasswordAgain.getText().toString()))
                {
                    ShowAlertDialogWindow("Пароли не совпадают!");
                }
                else {
                    registerUser();
                }

            }
        });
    }

    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(etMail.getText().toString());
        registerRequest.setFirstName(etName.getText().toString());
        registerRequest.setLastName(etSecondName.getText().toString());
        registerRequest.setPassword(etPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    String message = "Все ок...";
                    Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    String message = "Что то пошло не так";
                    Toast.makeText(ActivitySignUp.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = "Регистрация прошла успешна";
                Toast.makeText(ActivitySignUp.this, message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(ActivitySignUp.this,ActivitySignIn.class));
            }
        });
    }

    public void ShowAlertDialogWindow(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(ActivitySignUp.this).setMessage(s).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
        }).create();
        alertDialog.show();

    }
}