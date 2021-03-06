package com.example.movei;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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

public class section_patient extends AppCompatActivity {
    private String[] items = {"内科"};
    private list_info_doctor list_info_doctor = new list_info_doctor();
    private list_info_patient list_info_patient = new list_info_patient();
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
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ll_content, list_info_patient, "apple")
                .show(list_info_patient)
                .commit();

        ListView lv_drawer = (ListView) findViewById(R.id.lv_drawer);
        ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lv_drawer.setAdapter(mAdapter);
        final LinearLayout ll_drawer = (LinearLayout) findViewById(R.id.ll_drawer);
        final Toolbar finalToolbar = toolbar;
        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchFragment(position);
                Log.d("positionpositionpositionpositionpositionposition","vlue is"+position);
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
                        fragmentTransaction.replace(R.id.ll_content, new list_info_doctor());
                        finalToolbar.setTitle("内科");
                        break;
                }
                fragmentTransaction.commit();
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
}
