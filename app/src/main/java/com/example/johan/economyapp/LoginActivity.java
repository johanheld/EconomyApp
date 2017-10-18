package com.example.johan.economyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
    private Button btnRegister;
    private EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init()
    {
        etName = (EditText)findViewById(R.id.etName);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new BL());
    }

    private class BL implements View.OnClickListener{

        @Override
        public void onClick(View v)
        {
            String name = etName.getText().toString();
            SharedPreferences pref = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username",name);
            editor.commit();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
