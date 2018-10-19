package com.example.movei;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class info_nurse extends AppCompatActivity {
    private LinearLayout info_record;

    private String[] onduty_names = new String[100];
    private String[] onduty_sex = new String[100];
    private String[] onduty_isonduty = new String[100];
    private String[] onduty_level = new String[100];

    private String[] noonduty_names = new String[100];
    private String[] noonduty_sex = new String[100];
    private String[] noonduty_isonduty = new String[100];
    private String[] noonduty_level = new String[100];

    private TextView list_nurse_name;
    private TextView list_nurse_sex;
    private TextView list_nurse_isonduty;
    private TextView list_nurse_level;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_nurse);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();//获取传来的intent对象
        final String data=intent.getStringExtra("count");
        final String isonduty=intent.getStringExtra("isonduty");
        int count = Integer.parseInt(data);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_nurse_name = findViewById(R.id.list_nurse_name);
        list_nurse_sex = findViewById(R.id.list_nurse_sex);
        list_nurse_isonduty = findViewById(R.id.list_nurse_isonduty);
        list_nurse_level = findViewById(R.id.list_nurse_level);


        //不推荐的做法；
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        init(isonduty);

        if(isonduty.equals("true")){
            list_nurse_name.setText(onduty_names[count]);
            list_nurse_sex.setText(onduty_sex[count]);
            list_nurse_isonduty.setText(onduty_isonduty[count]);
            list_nurse_level.setText(onduty_level[count]);
        }else if (isonduty.equals("false")){
            list_nurse_name.setText(noonduty_names[count]);
            list_nurse_sex.setText(noonduty_sex[count]);
            list_nurse_isonduty.setText(noonduty_isonduty[count]);
            list_nurse_level.setText(noonduty_level[count]);
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
            Request request = new Request.Builder().url("http://192.168.43.184:8000/AndroidInterface/getnurse/").build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i=0,j=0,k=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nurse_name = jsonObject.getString("nurse_name");
                String nurse_sex = jsonObject.getString("nurse_sex");
                String nurse_isonduty = jsonObject.getString("nurse_isonduty");
                String nurse_level = jsonObject.getString("nurse_level");
                if(nurse_isonduty.equals("1")){
                    onduty_names[j]=nurse_name;
                    onduty_sex[j]=nurse_sex;
                    onduty_isonduty[j]="值班";
                    onduty_level[j]=nurse_level;
                    j++;
                }else if(nurse_isonduty.equals("0")){
                    noonduty_names[k]=nurse_name;
                    noonduty_sex[k]=nurse_sex;
                    noonduty_isonduty[k]="休息";
                    noonduty_level[k]=nurse_level;
                    k++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
