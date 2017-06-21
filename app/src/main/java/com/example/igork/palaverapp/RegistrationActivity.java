package com.example.igork.palaverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Intent;

import helper.NetworkHelper;
import helper.OnDownloadFinished;
import helper.ApiResult;
import helper.GeneralHelper;

public class RegistrationActivity extends AppCompatActivity implements OnDownloadFinished{

    ProgressDialog progressDialog;
    PalaverApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        app = (PalaverApplication)getApplication();
        app.setContext(this);
        Button registration = (Button)findViewById(R.id.regButton);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = ((EditText)findViewById(R.id.regNickname)).getText().toString();
                String password1 = ((EditText)findViewById(R.id.regPassword1)).getText().toString();
                String password2 = ((EditText)findViewById(R.id.regPassword2)).getText().toString();
                if(nickname != null && password1 != null && password2 != null) {
                    if(!password1.equals(password2)) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Die Passwörter stimmen nicht überein",
                                Toast.LENGTH_LONG)
                                .show();
                        EditText password1Field = (EditText) findViewById(R.id.regPassword1);
                        password1Field.setText("");
                        EditText password2Field = (EditText) findViewById(R.id.regPassword2);
                        password2Field.setText("");
                    }
                    else {
                        progressDialog = ProgressDialog.show(RegistrationActivity.this, "Registrierung", "Bitte warten...", false);
                        try{
                            new NetworkHelper(RegistrationActivity.this).execute(NetworkHelper.ApiCommand.USER_REGISTER.toString(),
                                    "{\"Username\":\"" + nickname + "\",\"Password\":\"" + password1 + "\"}");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

    }
    @Override
    public void onDownloadFinished(ApiResult result) {
        progressDialog.hide();
        try {
            if (GeneralHelper.getInstance().isSuccessful(result.json)) {
                app.setUsername(((EditText) findViewById(R.id.regNickname)).getText().toString());
                app.setPassword(((EditText) findViewById(R.id.regPassword1)).getText().toString());
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Der Nickname ist schon belegt", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
