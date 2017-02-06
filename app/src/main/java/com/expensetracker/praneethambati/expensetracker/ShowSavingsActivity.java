package com.expensetracker.praneethambati.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowSavingsActivity extends AppCompatActivity {

   ExpandableListView expandableListView;
    ExpandableListAdapter adapter;

    String mobile_no,reason,amount,startDate,endDate;


    String url = "http://androindian.com/apps/fm/api.php";
    JSONObject jsonoBject=null;
    JSONArray jsonArray;
    String respone="";
   // ArrayList<String> myMobileNums = new ArrayList<>();
    ArrayList<String> myAmounts = new ArrayList<>();
    ArrayList<String> myReasons = new ArrayList<>();

    HashMap<String,List<String>> listData = new HashMap<>();


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String userKey = "";
    public static final String passwordKey = "";
    public static final String profileKey="";
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_savings);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_listview1);
        expandableListView.setGroupIndicator(null);


        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        userID = preferences.getString("userKey","");


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action","get_savings");
            jsonObject.put("stype","S");
            jsonObject.put("user_id",userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Reg reg = new Reg();
        reg.execute(jsonObject.toString());


      //  setListener();
    }

    private void setListener() {
        // This listener will show toast on group click
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                Toast.makeText(ShowSavingsActivity.this,
                        "You clicked : " + adapter.getGroup(group_pos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        ShowSavingsActivity.this,
                        "You clicked : " + adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setItems() {
       // ArrayList<String> header = new ArrayList<String>();

        adapter = new ExpandableListAdapter(ShowSavingsActivity.this, myReasons, listData);

        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);




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
                    //JSONObject jsonObject1 = new JSONObject(s);

                    jsonArray = jsonoBject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        //   type_code = jsonObject2.getString("code");
                         mobile_no = jsonObject2.getString("mobile_no");
                        reason = jsonObject2.getString("reason");
                        amount = jsonObject2.getString("amount");
                        startDate = jsonObject2.getString("datetime");
                        endDate = jsonObject2.getString("enddate");

                     //   myMobileNums.add(mobile_no);
                        myReasons.add(reason);
                        myAmounts.add(amount);

                        List<String> childData = new ArrayList<>();
                        childData.add(mobile_no);
                        childData.add(amount);
                        childData.add(startDate);
                        childData.add(endDate);

                       listData.put(myReasons.get(i),childData);
                        // mySpinnerCode.add(type_code);
                        Log.e("ListData:", listData.toString());

                        //  mySpinner.put(type_code,type_name);
                    }
                    setItems();
                }


            } catch (JSONException e1) {
                e1.printStackTrace();

            }
        }
    }
}
