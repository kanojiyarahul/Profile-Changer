package com.example.rahulkanojiya.profilechanger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button button;
    EditText etg,ets,etv;
    SharedPreferences sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpref = getApplicationContext().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        Log.v("qscvhg","Main called");

        button = (Button)findViewById(R.id.bSave);
        etg = (EditText)findViewById(R.id.etGen);
        ets = (EditText)findViewById(R.id.etSil);
        etv = (EditText)findViewById(R.id.etVib);
        button.setOnClickListener(this);

        String gen = sharedpref.getString("general","null");
        String sil = sharedpref.getString("silent","null");
        String vib = sharedpref.getString("vibrate","null");

        if(!gen.equals("null")){
            etg.setText(gen);
        }
        if(!sil.equals("null")){
            ets.setText(sil);
        }
        if(!vib.equals("null")){
            etv.setText(vib);
        }

    }


    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("general",etg.getText().toString());
        editor.putString("silent",ets.getText().toString());
        editor.putString("vibrate",etv.getText().toString());
        editor.commit();

    }
}
