package com.expensetracker.praneethambati.expensetracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET,passwordET;
    Button loginBTN;

    String uname, upass;
    String respone="";
    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonoBject=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

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

//                    Intent regintent = new Intent(Second.this, Registrion.class);
//                    startActivity(regintent);

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
