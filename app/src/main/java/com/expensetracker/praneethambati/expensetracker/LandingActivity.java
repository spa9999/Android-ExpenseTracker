package com.expensetracker.praneethambati.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String userKey = "";
    public static final String passwordKey = "";
    public static final String profileKey = "";

    Button insertBTN,showBTN, createGroupBTN, showGroupBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        insertBTN = (Button) findViewById(R.id.insertBTN);
        showBTN = (Button) findViewById(R.id.showBTN);
        createGroupBTN = (Button) findViewById(R.id.createGroupBTN);
        showGroupBTN = (Button) findViewById(R.id.showGroupBTN);


        insertBTN.setOnClickListener(this);
        showBTN.setOnClickListener(this);
        createGroupBTN.setOnClickListener(this);
        showGroupBTN.setOnClickListener(this);
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
            case R.id.createGroupBTN:
                Intent create = new Intent(LandingActivity.this,CreateGroupActivity.class);
                startActivity(create);
                break;
            case R.id.showGroupBTN:
                Intent showg = new Intent(LandingActivity.this,ShowGroupActivity.class);
                startActivity(showg);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int myItem = item.getItemId();

        switch (myItem){
            case R.id.logout:
                SharedPreferences.Editor sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                sharedPreferences.clear();
                sharedPreferences.apply();
                Intent i = new Intent(LandingActivity.this,LoginActivity.class);
                startActivity(i);
        }

        return false;
    }
}
