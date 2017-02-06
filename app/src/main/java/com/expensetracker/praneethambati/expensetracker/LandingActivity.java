package com.expensetracker.praneethambati.expensetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {

    Button insertBTN,showBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        insertBTN = (Button) findViewById(R.id.insertBTN);
        showBTN = (Button) findViewById(R.id.showBTN);


        insertBTN.setOnClickListener(this);
        showBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insertBTN:
                Intent insert = new Intent(LandingActivity.this,InsertSavingsActivity.class);
                startActivity(insert);
                break;

            case R.id.showBTN:
                Intent show = new Intent(LandingActivity.this,ShowSavingsActivity.class);
                startActivity(show);
                break;
        }
    }
}
