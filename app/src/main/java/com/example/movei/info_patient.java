package com.example.movei;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class info_patient extends AppCompatActivity {
    private LinearLayout info_record;
    private TextView name;
    private TextView sex;
    private TextView phone;
    private TextView address;
    @SuppressLint("LongLogTag")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_patient);
        name = findViewById(R.id.patient_name);
        sex = findViewById(R.id.patient_sex);
        phone = findViewById(R.id.patient_phone);
        address = findViewById(R.id.patient_address);
        Intent intent=getIntent();
        final String Patient_id=intent.getStringExtra("patient_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init(Patient_id);
        info_record = findViewById(R.id.info_record);
        info_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),info_record.class);
                intent.putExtra("record_id", Patient_id);//设置参数,""
                startActivity(intent);
            }
        });
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
    @SuppressLint("WrongViewCast")
    private void init(String patient_id){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.184:8000/AndroidInterface/getpatient/").build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String section_id = jsonObject.getString("section_id");
                String patient_name = jsonObject.getString("patient_name");
                String patient_sex = jsonObject.getString("patient_sex");
                String patient_phone = jsonObject.getString("patient_phone");
                String patient_address = jsonObject.getString("patient_address");
                if(i==Integer.parseInt(patient_id)){
                    name.setText(patient_name);
                    sex.setText(patient_sex);
                    phone.setText(patient_phone);
                    address.setText(patient_address);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
