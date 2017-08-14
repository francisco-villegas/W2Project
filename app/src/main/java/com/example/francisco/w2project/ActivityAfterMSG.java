package com.example.francisco.w2project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityAfterMSG extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_msg);

        Intent intent = getIntent();
        String message = intent.getStringExtra("key1");

        TextView tv1 = (TextView) findViewById(R.id.tv1);

        tv1.setText(message);
    }
}
