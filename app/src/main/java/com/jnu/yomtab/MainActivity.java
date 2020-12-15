package com.jnu.yomtab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jnu.yomtab.data.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_ADDNEW = CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_ADDNEW+1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK= 902;
    //PersonAdapter bookAdapter;//自定义适配器BookAdapter
    private List<Person> People = new ArrayList<>();//创建ArrayList
    PersonAdapter personAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private Button startButtonActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        init2();
        initViewpager();
        startButtonActivity = (Button)findViewById(R.id.button);
        registerForContextMenu(startButtonActivity);//将菜单绑定在view上

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
        menu.setHeaderTitle("导航栏");
        if(v == findViewById(R.id.upp)) {
            //获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(People.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            //用const 将常数变为宏变量 更具连贯性
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ADDNEW, 0, "新建");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (901):
                if(resultCode == RESULT_OK){
                    String title = data.getStringExtra("title");
                    int insertPosition = data.getIntExtra("insert_position",0);
                    People.add(insertPosition,new Person(title));
                    personAdapter.notifyDataSetChanged();
                }
        }
    }

    //公用的菜单选项
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shouli:
                        Intent intent = new Intent(this,ButtonMainActivity.class);
                        intent.putExtra("title","ssj");
                        intent.putExtra("insert_position","ssj ");
                        startActivityForResult(intent,901);
                break;
            case R.id.home:
                Intent intent2 = new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.suili:
                Toast.makeText(this, "hhd", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel:

                break;
            case R.id.upp:
            default:
        }
        return super.onContextItemSelected(item);
    }
    private void init(){
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragments = new ArrayList<Fragment>();
        titles = new ArrayList<String>();

    }
    private void initViewpager(){
        FragmentAdapter myPageAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragments.add(new upFragment(personAdapter));
        fragments.add(new CalendarFragment());
        fragments.add(new downFragment());
        myPageAdapter.setData(fragments);
        //fragment_pageradpter adpter = new fragment_pageradpter(getSupportFragmentManager(),fragments,null);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setCurrentItem(1);//选中初始tab
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
        personAdapter = new PersonAdapter(
                MainActivity.this, R.layout.person_item, People);//加不加android有什么区别吗
        People.add(new Person("张三"));
        People.add(new Person("王五"));
        People.add(new Person("李四"));
        //myPageAdapter.setData(People);
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

