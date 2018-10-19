package com.example.movei;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class doctor extends Fragment {
    public static final int SHOW_RESPONSE = 0;
    private Context main;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    //适配器
    private String[] names = new String[100];
    private String[] names_no = new String[100];
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.charts_fragment, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_view);
        mTabLayout = view.findViewById(R.id.tabs);
        mInflater = LayoutInflater.from(main);
        //不推荐的做法；
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        init();

        view1 = mInflater.inflate(R.layout.layout_hello, null);
        // 自己的适配器
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<names.length&&names[i]!=null;i++){
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("name", names[i]);
            showitem.put("name_no", names_no[i]);
            listitem.add(showitem);
        }
        SimpleAdapter myAdapter = new SimpleAdapter(this.getActivity().getApplicationContext(),listitem, R.layout.list_info, new String[]{"name","name_no"}, new int[]{R.id.name_section,R.id.name_section_no});
        ListView listView = (ListView) view1.findViewById(R.id.list_test);
        listView.setAdapter(myAdapter);
        // 设置监听器
        listView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity().getApplicationContext(),section_doctor.class);
                startActivity(intent);

            }

        }) ;
        view2 = mInflater.inflate(R.layout.layout_hello, null);
        List<Map<String, Object>> listitem_view2 = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<names.length&&names[i]!=null;i++){
            Map<String, Object> showitem2 = new HashMap<String, Object>();
            showitem2.put("name", names[i]);
            showitem2.put("name_no", names_no[i]);
            listitem_view2.add(showitem2);
        }
        SimpleAdapter myAdapter_view2 = new SimpleAdapter(this.getActivity().getApplicationContext(),listitem, R.layout.list_info, new String[]{"name","name_no"}, new int[]{R.id.name_section,R.id.name_section_no});
        ListView listView_view2 = (ListView) view2.findViewById(R.id.list_test);
        listView_view2.setAdapter(myAdapter_view2);
        // 设置监听器
        listView_view2.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity().getApplicationContext(),section_nurse.class);
                startActivity(intent);

            }

        }) ;

        view3 = mInflater.inflate(R.layout.layout_hello, null);
        List<Map<String, Object>> listitem_view3 = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<names.length&&names[i]!=null;i++){
            Map<String, Object> showitem3 = new HashMap<String, Object>();
            showitem3.put("name", names[i]);
            showitem3.put("name_no", names_no[i]);
            listitem_view3.add(showitem3);
        }
        SimpleAdapter myAdapter_view3 = new SimpleAdapter(this.getActivity().getApplicationContext(),listitem, R.layout.list_info, new String[]{"name","name_no"}, new int[]{R.id.name_section,R.id.name_section_no});
        ListView listView_view3 = (ListView) view3.findViewById(R.id.list_test);
        listView_view3.setAdapter(myAdapter_view3);
        // 设置监听器
        listView_view3.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity().getApplicationContext(),section_patient.class);
                startActivity(intent);

            }

        }) ;
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        //添加页卡标题
        mTitleList.add("医生");
        mTitleList.add("护士");
        mTitleList.add("病人");

        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)), true);

        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            main = (Context) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;
        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }


    private void init(){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://192.168.43.184:8000/AndroidInterface/getsection/").build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONArray jsonArray = new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String section_no = jsonObject.getString("section_no");
                String section_id = jsonObject.getString("section_id");
                String section_name = jsonObject.getString("section_name");
                names[i]=section_name;
                names_no[i]=section_no;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
