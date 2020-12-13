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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PersonAdapter bookAdapter;//自定义适配器BookAdapter
    private List<Person> People = new ArrayList<>();//创建ArrayList
    PersonAdapter personAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        personAdapter = new PersonAdapter(
                MainActivity.this, R.layout.person_item, People);
        PersonFragmentAdapter myPageAdapter = new PersonFragmentAdapter(getSupportFragmentManager());
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("收礼");
        titles.add("主页");
        titles.add("随礼");
        myPageAdapter.setTitles(titles);

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new upFragment(personAdapter));//收礼
        datas.add(new CalendarFragment());//主页
        datas.add(new upFragment(personAdapter));//随礼myPageAdapter.setData(datas);
        myPageAdapter.setData(datas);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
// 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
// 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);
        final Button startButtonActivity = (Button)findViewById(R.id.button);
        startButtonActivity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init() {
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

