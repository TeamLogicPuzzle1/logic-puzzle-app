package com.cookandroid.test_ui;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FilterCheck extends AppCompatActivity {

    Button btnLoc1, btnLoc2, btnLoc3, btnFit1, btnFit2, btnFit3, btnFit4, btnFit5, btnFit6, btnFit7, btnFit8;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        btnLoc1 = (Button) findViewById(R.id.BtnLoc1);
        btnLoc2 = (Button) findViewById(R.id.BtnLoc2);
        btnLoc3 = (Button) findViewById(R.id.BtnLoc3);

        btnFit1 = (Button) findViewById(R.id.BtnFit1);
        btnFit2 = (Button) findViewById(R.id.BtnFit2);
        btnFit3 = (Button) findViewById(R.id.BtnFit3);
        btnFit4 = (Button) findViewById(R.id.BtnFit4);
        btnFit5 = (Button) findViewById(R.id.BtnFit5);
        btnFit6 = (Button) findViewById(R.id.BtnFit6);
        btnFit7 = (Button) findViewById(R.id.BtnFit7);
        btnFit8 = (Button) findViewById(R.id.BtnFit8);


    }
}
