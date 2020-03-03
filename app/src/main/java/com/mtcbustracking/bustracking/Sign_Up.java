package com.mtcbustracking.bustracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Sign_Up extends AppCompatActivity {

    Configurations config;
    TextInputLayout name, mail, mob, pass, area;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign__up);


        config = new Configurations();
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        mob = findViewById(R.id.mob);
        pass = findViewById(R.id.pass);
        area = findViewById(R.id.area);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getEditText().getText().toString();
                String umail = mail.getEditText().getText().toString();
                String umob = mob.getEditText().getText().toString();
                String upass = pass.getEditText().getText().toString();
                String uarea = area.getEditText().getText().toString();
                config.addUser(Sign_Up.this, uname, umail, umob, upass, uarea);
            }
        });

    }
}
