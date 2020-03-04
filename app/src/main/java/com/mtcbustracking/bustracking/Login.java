package com.mtcbustracking.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputLayout uname, pass;
    TextView acct;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Configurations config = new Configurations();
        uname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        acct = findViewById(R.id.new_account);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uname.getEditText().getText().toString().equalsIgnoreCase("admin") && pass.getEditText().getText().toString().equalsIgnoreCase("admin")){
                    startActivity(new Intent(Login.this, Driver.class));
                }else {
                    config.login(Login.this, uname.getEditText().getText().toString(), pass.getEditText().getText().toString());
                }


            }
        });

        acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Sign_Up.class));
            }
        });

    }
}
