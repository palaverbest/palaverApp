package com.example.igork.palaverapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.ProgressDialog;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import helper.NetworkHelper;
import helper.ApiResult;
import helper.GeneralHelper;
import helper.OnDownloadFinished;

public class LoginActivity extends AppCompatActivity implements OnDownloadFinished {

    ProgressDialog progressDialog;
    PalaverApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (PalaverApplication)getApplication();
        app.setContext(this);

        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = ((EditText)findViewById(R.id.login_nickname)).getText().toString();
                String password = ((EditText)findViewById(R.id.login_password)).getText().toString();
                progressDialog = progressDialog.show(LoginActivity.this, "Login...", "Warten", false);
                try{
                    new NetworkHelper(LoginActivity.this)
                            .execute(NetworkHelper.ApiCommand.USER_VALIDATE.toString(), "{\"Username\":\"" + nickname + "\",\"Password\":\"" + password + "\"}");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextView linkToRegistration = (TextView) findViewById(R.id.link_to_registration);
        linkToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

    @Override
    public void onDownloadFinished(ApiResult result) {
        progressDialog.hide();
        try {
            if(GeneralHelper.getInstance().isSuccessful(result.json)) {
                app.setUsername(((EditText) findViewById(R.id.login_nickname)).getText().toString());
                app.setPassword(((EditText) findViewById(R.id.login_password)).getText().toString());
                startActivity(new Intent(LoginActivity.this, ChatActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Logindaten sind falsch!", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {e.printStackTrace();}
    }
}
