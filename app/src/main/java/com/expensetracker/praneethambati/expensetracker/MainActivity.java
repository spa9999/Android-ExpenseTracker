package com.expensetracker.praneethambati.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String userKey = "";
    public static final String passwordKey = "";
    public static final String profileKey="";
    String userName="";
    //String password="";
    String profile="";
    EditText usernameET;
    Spinner savingstypeSpnr;
    ArrayAdapter spinneradapter;

    JSONObject jsonoBject;
    JSONArray jsonArray;
    String url = "http://androindian.com/apps/fm/api.php";
    String type_code,type_name;
    String selectedSavingsType="";

    ArrayList<String> mySpinnerNames=new ArrayList<String>();
    ArrayList<String> mySpinnerCode =new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userName = preferences.getString("userKey","");
       // password = preferences.getString("pwdKey","");
        profile = preferences.getString("profileKey","");

        usernameET = (EditText) findViewById(R.id.mobileET);
        savingstypeSpnr = (Spinner) findViewById(R.id.savingtypeSpnr);

        //username txt
        usernameET.setText(userName.toString());


        //Getting data for SPINNER ....

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action","get_saving_types");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Jsonspinner jsonspinner = new Jsonspinner();
        jsonspinner.execute(jsonObject.toString());



        //Spinner data from Web service
         spinneradapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, mySpinnerNames);

savingstypeSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            String code = savingstypeSpnr.getItemAtPosition(position).toString();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subObj = jsonArray.getJSONObject(i);
                String d_nm = subObj.getString("name".trim());
                if(code.equals(d_nm)){
                    selectedSavingsType = subObj.getString("code".trim());
                   // System.out.println("The Selected Unit::::"+selectedSavingsType);
                   // Toast.makeText(MainActivity.this,""+selectedSavingsType,Toast.LENGTH_LONG).show();

                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});








    }

    private class Jsonspinner extends AsyncTask<String,String,String>{


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
                JSONObject jsonObject1 = new JSONObject(s);

                 jsonArray = jsonObject1.getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                 //   type_code = jsonObject2.getString("code");
                    type_name = jsonObject2.getString("name");

                    mySpinnerNames.add(type_name);
                   // mySpinnerCode.add(type_code);
                    Log.e("SpinnerNames:",mySpinnerNames.toString());
                  //  mySpinner.put(type_code,type_name);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            savingstypeSpnr.setAdapter(spinneradapter);
        }
    }


}
