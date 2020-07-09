package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "SettingsActivity";
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Log.i(TAG, "Should be null: " + ParseUser.getCurrentUser());
                Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}