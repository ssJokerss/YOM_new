package com.jnu.yomtab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jnu.yomtab.data.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PersonAdapter bookAdapter;//自定义适配器BookAdapter
    private List<Person> People = new ArrayList<>();//创建ArrayList
    PersonAdapter personAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViewpager();
        init2();
        final Button startButtonActivity = (Button)findViewById(R.id.button);
        startButtonActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,ButtonMainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        //设置是否开启ViewPager滑动
        //viewPager.setPagingEnabled(true);
        fragments = new ArrayList<Fragment>();
        titles = new ArrayList<String>();

    }
    private void initViewpager(){
        fragments.add(new upFragment());
        fragments.add(new CalendarFragment());
        fragments.add(new downFragment());

        fragment_pageradpter adpter = new fragment_pageradpter(getSupportFragmentManager(),fragments,null);
        viewPager.setAdapter(adpter);
        new_tab();
    }
    private void new_tab(){
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("随礼",R.drawable.ic_down)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("主页",R.drawable.ic_home)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("收礼",R.drawable.ic_up)));
        //Tablayout自定义view绑定ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
    private View tab_icon(String name,int iconID){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.icon_view,null);
        TextView tv = (TextView) newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        ImageView im = (ImageView)newtab.findViewById(R.id.tabicon);
        im.setImageResource(iconID);
        return newtab;
    }
    private void init2() {
        People.add(new Person("张三"));
        People.add(new Person("王五"));
        People.add(new Person("李四"));
    }

    //自定义适配器BookAdapter
    public class PersonAdapter extends ArrayAdapter<Person> {

        private int resourceId;

        public PersonAdapter(Context context, int resource, List<Person> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person person = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            // ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());//显示图片
            ((TextView) view.findViewById(R.id.person_item_kongjian)).setText(person.getTitle());//显示名称
            return view;
        }
    }
}

