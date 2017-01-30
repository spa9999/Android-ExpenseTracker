package com.expensetracker.praneethambati.expensetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    EditText nameRegET,mobileRegET,emailRegET,passwordRegET,passwordReg2ET;
    Button registerBTN;

    String name, mobile,email,password,password2;
    String respone="";
    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonoBject=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameRegET = (EditText) findViewById(R.id.nameRegET);
        mobileRegET = (EditText) findViewById(R.id.mobileRegET);
        emailRegET = (EditText) findViewById(R.id.emailRegET);
        passwordRegET = (EditText) findViewById(R.id.passwordRegET);
        passwordReg2ET = (EditText) findViewById(R.id.passwordReg2ET);

        registerBTN = (Button) findViewById(R.id.registerBTN);

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordRegET.getText().toString();
                password2 = passwordReg2ET.getText().toString();
                if(!password.equals(password2)){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistrationActivity.this);

                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please verify the Passwords"+password+password2);
                    alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }

               else {
                    name = nameRegET.getText().toString().trim();
                    mobile = mobileRegET.getText().toString().trim();
                    email = emailRegET.getText().toString().trim();
                    password = passwordRegET.getText().toString().trim();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name",name);
                        jsonObject.put("mobile",mobile);
                        jsonObject.put("email",email);
                        jsonObject.put("pswrd",password);
                        jsonObject.put("action","register_user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Reg reg = new Reg();
                    reg.execute(jsonObject.toString());
                    Log.e("JSON Values:",jsonObject.toString());

                }
            }
        });
    }

    private class Reg extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            jsonoBject = JsonFunction.getJsonFromUrlparam(url, params[0]);
            Log.i("json", "" + jsonoBject);
            return String.valueOf(jsonoBject);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(jsonoBject.toString());
                respone = jsonObject.getString("response");

                if (respone.trim().equals("success")) {

                    String status = jsonObject.getString("user");
                    Toast.makeText(getApplicationContext(), "" + status, Toast.LENGTH_LONG).show();

                    Intent regintent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(regintent);

                } else if (respone.trim().equals("failed")) {
                    String status1 = jsonObject.getString("user");
                    Toast.makeText(getApplicationContext(), "" + status1, Toast.LENGTH_LONG).show();


                } else if (respone.trim().equals("error")) {
                    String status2 = jsonObject.getString("user");
                    Toast.makeText(getApplicationContext(), "" + status2, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
