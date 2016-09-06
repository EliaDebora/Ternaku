package com.fintech.ternaku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity2 extends AppCompatActivity {
    private EditText edtUsername,edtPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        edtUsername = (EditText)findViewById(R.id.username);
        edtPass = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity2.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
