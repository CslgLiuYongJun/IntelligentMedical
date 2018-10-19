package com.example.movei;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.lang.Math.log;

public class info_doctor extends AppCompatActivity {
    private LinearLayout info_record;
    //适配器
    private String[] names = new String[100];
    private String[] sex = new String[100];
    private String[] isonduty = new String[100];
    private String[] level = new String[100];

    private String[] onduty_names = new String[100];
    private String[] onduty_names_no = new String[100];
    private String[] onduty_sex = new String[100];
    private String[] onduty_isonduty = new String[100];
    private String[] onduty_level = new String[100];

    private String[] noonduty_names = new String[100];
    private String[] noonduty_names_no = new String[100];
    private String[] noonduty_sex = new String[100];
    private String[] noonduty_isonduty = new String[100];
    private String[] noonduty_level = new String[100];


    private TextView list_doctor_name;
    private TextView list_doctor_sex;
    private TextView list_doctor_isonduty;
    private TextView list_doctor_level;

    @SuppressLint("LongLogTag")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_doctor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();//获取传来的intent对象
        final String data=intent.getStringExtra("count");
        final String isonduty=intent.getStringExtra("isonduty");
        int count = Integer.parseInt(data);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_doctor_name = findViewById(R.id.list_doctor_name);
        list_doctor_sex = findViewById(R.id.list_doctor_sex);
        list_doctor_isonduty = findViewById(R.id.list_doctor_isonduty);
        list_doctor_level = findViewById(R.id.list_doctor_level);


        //不推荐的做法；
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        init(isonduty);

        if(isonduty.equals("true")){
            list_doctor_name.setText(onduty_names[count]);
            list_doctor_sex.setText(onduty_sex[count]);
            list_doctor_isonduty.setText(onduty_isonduty[count]);
            list_doctor_level.setText(onduty_level[count]);
        }else if (isonduty.equals("false")){
            list_doctor_name.setText(noonduty_names[count]);
            list_doctor_sex.setText(noonduty_sex[count]);
            list_doctor_isonduty.setText(noonduty_isonduty[count]);
            list_doctor_level.setText(noonduty_level[count]);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(String isonduty){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.184:8000/AndroidInterface/getdoctor/").build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i=0,j=0,k=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String section_no = jsonObject.getString("doctor_no");
                String doctor_name = jsonObject.getString("doctor_name");
                String doctor_sex = jsonObject.getString("doctor_sex");
                String doctor_isonduty = jsonObject.getString("doctor_isonduty");
                String doctor_level = jsonObject.getString("doctor_level");
                if(doctor_isonduty.equals("值班")){
                    onduty_names[j]=doctor_name;
                    onduty_sex[j]=doctor_sex;
                    onduty_isonduty[j]=doctor_isonduty;
                    onduty_level[j]=doctor_level;
                    j++;
                }else if(doctor_isonduty.equals("休息")){
                    noonduty_names[k]=doctor_name;
                    noonduty_sex[k]=doctor_sex;
                    noonduty_isonduty[k]=doctor_isonduty;
                    noonduty_level[k]=doctor_level;
                    k++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
