package com.expensetracker.praneethambati.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String userKey = "";
    public static final String passwordKey = "";
    public static final String profileKey="";

    String userName="";
    String password="";
    String profile="";
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                userName = preferences.getString("userKey","");
                password = preferences.getString("pwdKey","");
                profile = preferences.getString("profileKey","");

                if(userName.equals("") && password.equals("")){
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Username:"+userName+"\nPassword:"+password+"\nProfile:"+profile, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SplashActivity.this, LandingActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
