package com.expensetracker.praneethambati.expensetracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InsertSavingsActivity extends AppCompatActivity implements View.OnClickListener {
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
    String code;
    
    ArrayList<String> mySpinnerNames=new ArrayList<String>();
    ArrayList<String> mySpinnerCode =new ArrayList<String>();

    //All layout fields references
    Button isavingsBTN;
    EditText reasonET,amountET,startDateET,endDateET;
    DatePickerDialog startDatePickerDialog,endDatePickerDialog;
    SimpleDateFormat dateFormatter;
     String respone="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userName = preferences.getString("userKey","");
       // password = preferences.getString("pwdKey","");
        profile = preferences.getString("profileKey","");

        usernameET = (EditText) findViewById(R.id.mobileET);
        savingstypeSpnr = (Spinner) findViewById(R.id.savingtypeSpnr);

        isavingsBTN = (Button) findViewById(R.id.isavingsBTN);
        reasonET = (EditText) findViewById(R.id.reasonET);
        amountET = (EditText) findViewById(R.id.amountET);
        startDateET = (EditText) findViewById(R.id.startDateET);
        startDateET.setInputType(InputType.TYPE_NULL);
        endDateET = (EditText) findViewById(R.id.endDateET);
        endDateET.setInputType(InputType.TYPE_NULL);
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
         spinneradapter = new ArrayAdapter(InsertSavingsActivity.this, android.R.layout.simple_spinner_dropdown_item, mySpinnerNames);

savingstypeSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
             code = savingstypeSpnr.getItemAtPosition(position).toString();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subObj = jsonArray.getJSONObject(i);
                String d_nm = subObj.getString("name".trim());
                if(code.equals(d_nm)){
                    selectedSavingsType = subObj.getString("code".trim());
                   // System.out.println("The Selected Unit::::"+selectedSavingsType);
                   // Toast.makeText(InsertSavingsActivity.this,""+selectedSavingsType,Toast.LENGTH_LONG).show();

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


        isavingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = userName.toString();
                String stype = selectedSavingsType.toString();
                String reason = reasonET.getText().toString();
                int amount = Integer.parseInt(amountET.getText().toString());
                String startDate = startDateET.getText().toString();
                String endDate =endDateET.getText().toString();

             //   Toast.makeText(getApplicationContext(),""+mobile+"\n"+stype+"\n"+reason+"\n"+amount+"\n"+startDate+"\n"+endDate+"\n",Toast.LENGTH_LONG).show();


                JSONObject jsonObject5 = new JSONObject();
                try {
                    jsonObject5.put("action","put_saving");
                    jsonObject5.put("mobile",mobile);
                    jsonObject5.put("stype",stype);
                    jsonObject5.put("reason",reason);
                    jsonObject5.put("amount",amount);
                    jsonObject5.put("start_date",startDate);
                    jsonObject5.put("end_date",endDate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Reg reg = new Reg();
                reg.execute(jsonObject5.toString());
                Log.e("JSON Values:",jsonObject5.toString());


            }
        });


        startDateET.setOnClickListener(this);
        endDateET.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                startDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));

        endDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                endDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));



    }

    @Override
    public void onClick(View v) {
        if(v == startDateET) {
            startDatePickerDialog.show();
        }
        else if(v == endDateET){
            endDatePickerDialog.show();
        }
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


    private class Reg extends AsyncTask<String,String,String>{
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

                    String call_back = jsonObject.getString("call_back");
                    Toast.makeText(getApplicationContext(), "" + call_back, Toast.LENGTH_LONG).show();

                   // Intent regintent = new Intent(RegistrationActivity.this, LoginActivity.class);
                   // startActivity(regintent);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
