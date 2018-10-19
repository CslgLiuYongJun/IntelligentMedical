package com.example.movei;




import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private String[] items = {"首页", "日志", "设置", "我的"};
    private doctor doctor = new doctor();
    private notice notice = new notice();
    private Thread newThread;
    private int finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mToggle.syncState();

        mDrawerLayout.addDrawerListener(mToggle);

        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ll_content, doctor, "apple")
                .show(doctor)
                .commit();


        ListView lv_drawer = (ListView) findViewById(R.id.lv_drawer);
        ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lv_drawer.setAdapter(mAdapter);
        final LinearLayout ll_drawer = (LinearLayout) findViewById(R.id.ll_drawer);
        final Toolbar finalToolbar = toolbar;
        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchFragment(position);
            }

            public void switchFragment(int fragmentId) {
                mDrawerLayout.closeDrawer(ll_drawer);
                int currentFragmentId = 5;
                if(currentFragmentId == fragmentId)
                    return;
                currentFragmentId = fragmentId;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (fragmentId)
                {
                    case 0:
                        fragmentTransaction.replace(R.id.ll_content, new doctor());
                        finalToolbar.setTitle("首页");
                        break;
                    case 1:
                        fragmentTransaction.replace(R.id.ll_content, new doctor());
                        finalToolbar.setTitle("日志");
                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.ll_content, new doctor());
                        finalToolbar.setTitle("设置");
                        break;
                    case 3:
                        fragmentTransaction.replace(R.id.ll_content, new notice());
                        finalToolbar.setTitle("我的");
                }
                fragmentTransaction.commit();
            }
        });

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(finished==0){
                    init();
                }
            }
        },1000,2000);

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,result_scan.class);
            intent.putExtra("patient_name", result);//设置参数,""
            startActivity(intent);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.scan:
                Intent intent_search = new Intent(getApplicationContext(),info_search.class);
                startActivity(intent_search);
                break;
            case R.id.search:
                startActivityForResult(new Intent(MainActivity.this,CaptureActivity.class),0);
                break;
            case R.id.set:
                startActivityForResult(new Intent(MainActivity.this,remind.class),0);
                break;
        }
        return true;
    }

    private void init(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.184:8000/AndroidInterface/getpatient/").build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String patient_id = jsonObject.getString("patient_id");
                String patient_free2 = jsonObject.getString("patient_free2");
                if(patient_free2.equals("1")&&patient_id.equals("1")){
                    finished=1;
                    Intent intent = new Intent(MainActivity.this,result_scan.class);
                    intent.putExtra("patient_name", "郭得友");//设置参数,""
                    startActivity(intent);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
