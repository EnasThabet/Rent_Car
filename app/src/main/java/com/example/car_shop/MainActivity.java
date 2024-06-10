package com.example.car_shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton loginButton;
    private ImageButton accountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login );
        accountButton = findViewById(R.id.account);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogOnclick(v);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAccOnclick(v);
            }
        });
    }

    public void btnLogOnclick(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

    public void btnAccOnclick(View view) {
        Intent intent = new Intent(MainActivity.this, AddCar.class);
        startActivity(intent);
    }
}
 