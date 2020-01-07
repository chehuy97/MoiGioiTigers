package com.example.moigioitiges.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.moigioitiges.view.broker.BrokerActivity;
import com.example.moigioitiges.view.customer.CustomerActivity;

import java.io.File;


public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");

        // Start home activity
        if (author=="")
            startActivity(new Intent(SplashScreenActivity.this, CustomerActivity.class));
        else
            startActivity(new Intent(SplashScreenActivity.this, BrokerActivity.class));

        // close splash activity
        finish();
    }
}
