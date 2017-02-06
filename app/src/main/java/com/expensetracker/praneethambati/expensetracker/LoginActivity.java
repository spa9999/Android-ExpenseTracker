package com.expensetracker.praneethambati.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET,passwordET;
    Button loginBTN;
    TextView registerTV;
    CheckBox rememberCB;

    Boolean rememberMeToggle = false;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String userKey = "userKey";
    public static final String passwordKey = "pwdKey";
    public static final String profileKey = "profileKey";

    SharedPreferences preferences;

    String uname, upass;
    String respone="";
    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonoBject=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rememberCB = (CheckBox) findViewById(R.id.remembermeCB);

        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        registerTV = (TextView) findViewById(R.id.registerTV);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });

        rememberCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    rememberMeToggle = true;
                }
                else{
                    rememberMeToggle = false;
                }
            }
        });

        loginBTN = (Button) findViewById(R.id.loginBTN);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = usernameET.getText().toString().trim();
                upass = passwordET.getText().toString().trim();

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("mobile",uname);
                    jsonObject.put("pswrd",upass);
                    jsonObject.put("action","login_user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Reg reg = new Reg();
                reg.execute(jsonObject.toString());
                Log.e("JSON Values:",jsonObject.toString());
            }
        });
    }

    private class Reg extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            jsonoBject = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("json", "" + jsonoBject);
            return String.valueOf(jsonoBject);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(jsonoBject.toString());
                respone = jsonObject.getString("response");

                if (respone.trim().equals("success")) {

                    String status = jsonObject.getString("data");
                    Toast.makeText(getApplicationContext(), "" + status, Toast.LENGTH_LONG).show();

                    if(rememberMeToggle==true) {
                        String user = uname;
                        String pwd = upass;

                        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor store = preferences.edit();
                        store.putString(userKey, user);
                        store.putString(passwordKey, pwd);
                        store.putString(profileKey, status);
                        store.commit();
                        Intent loginIntent = new Intent(LoginActivity.this, InsertSavingsActivity.class);
                        startActivity(loginIntent);
                    }
                    else{
                        Intent loginIntent = new Intent(LoginActivity.this, LandingActivity.class);
                        loginIntent.putExtra("profile",status);
                        startActivity(loginIntent);
                    }



                } else if (respone.trim().equals("failed")) {
                    //String status1 = jsonObject.getString("user");
                    Toast.makeText(getApplicationContext(), "" + "Failed", Toast.LENGTH_LONG).show();


                } else if (respone.trim().equals("error")) {
                   // String status2 = jsonObject.getString("user");
                    Toast.makeText(getApplicationContext(), "" + "Error ", Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
