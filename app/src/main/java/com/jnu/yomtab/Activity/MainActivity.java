package com.jnu.yomtab.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jnu.yomtab.fragment.CalendarFragment;
import com.jnu.yomtab.R;
import com.jnu.yomtab.adapter.FragmentAdapter;
import com.jnu.yomtab.data.Person;
import com.jnu.yomtab.fragment.downFragment;
import com.jnu.yomtab.fragment.upFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.jnu.yomtab.fragment.downFragment.arrayList_person;
import static com.jnu.yomtab.fragment.upFragment.Peoplenew;
import static com.jnu.yomtab.fragment.downFragment.People2;
import static com.jnu.yomtab.fragment.downFragment.DownAdapter;
public class MainActivity extends AppCompatActivity {
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_ADDNEW = CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_ADDNEW+1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK= 902;

    public static List<Person> People = new ArrayList<>();//创建ArrayListPeople收纳person数组
    public static PersonAdapter personAdapter;                    //personAdapter适配器
    //public static downAdapter DownAdapter;                    //DownAdapter适配器
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private Button startButtonActivity;             //记一笔button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//创建tablayout和viewpager
        init2();

        initViewpager();//fragment和Viewpager相绑定
        //toolbarinit();
        startButtonActivity = (Button)findViewById(R.id.button);
        registerForContextMenu(startButtonActivity);//将菜单绑定在view上

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        //button的导航栏
        if (v == findViewById(R.id.button)){
            inflater.inflate(R.menu.menu, menu);
            menu.setHeaderTitle("导航栏");
        }
    }
    //公用的菜单选项
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //收礼界面
            case R.id.shouli:
                Intent intent = new Intent(this,ButtonMainActivity.class);
                intent.putExtra("title", "姓名");
                intent.putExtra("insert_position",0);
                intent.putExtra("money","金钱");
                intent.putExtra("date", CalendarFragment.time);
                intent.putExtra("reason", "缘由");
                startActivityForResult(intent,901);
                break;
            case R.id.home:
                Intent intent2 = new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.suili:
                Intent intent3 = new Intent(this,ButtonMainActivity.class);
                intent3.putExtra("title", "姓名");
                intent3.putExtra("insert_position",0);
                intent3.putExtra("money","金钱");
                intent3.putExtra("date", CalendarFragment.time);
                intent3.putExtra("reason", "缘由");
                startActivityForResult(intent3,902);
                break;
            case R.id.cancel:
            default:
        }
        return super.onContextItemSelected(item);
    }
    //活动之间的响应
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case (901):
                if(resultCode == RESULT_OK){

                    String title = data.getStringExtra("title");//传入的title值
                    String money = data.getStringExtra("money");//初始值
                    String date = data.getStringExtra("date");//初始值
                    String reason = data.getStringExtra("reason");
                    People.add(new Person(title,money,date,reason));
                    Peoplenew.add(new Person(title,money,date,reason));
                    personAdapter.notifyDataSetChanged();//数据更新
                    CalendarFragment.updateDate(date);
                    CalendarFragment.DownAdapter_calender.notifyDataSetChanged();
                }
            case (902):
                if(resultCode == RESULT_OK){

                    String title = data.getStringExtra("title");//传入的title值
                    String money = data.getStringExtra("money");//初始值
                    String date = data.getStringExtra("date");//初始值
                    String reason = data.getStringExtra("reason");
                    arrayList_person.add(new Person(title,money,date,reason));
                    downFragment.initDate();
                    //Peoplenew.add(new Person(title,money,date,reason));
                    DownAdapter.notifyDataSetChanged();//数据更新
                    CalendarFragment.updateDate(date);
                    CalendarFragment.DownAdapter_calender.notifyDataSetChanged();
                }
        }
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
        fragments.add(new downFragment(DownAdapter));
        myPageAdapter.setData(fragments);
        viewPager.setAdapter(myPageAdapter);
        new_tab();
        viewPager.setCurrentItem(1);
    }
    private void new_tab(){
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("收礼",R.drawable.ic_shouli)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("主页",R.drawable.ic_zhuye)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab_icon("随礼",R.drawable.ic_songli)));
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
        personAdapter = new MainActivity.PersonAdapter(MainActivity.this, R.layout.person_item, People);
        if(People.size()==0){//博文牛逼
            People.add(new Person("张三","500￥","2015-10-1","婚礼"));
            People.add(new Person("王五","500￥","2015-10-2","生日"));
        }
        arrayList_person.add(new Person("博文","100","2019-01-01","婚礼"));
        arrayList_person.add(new Person("博文","100","2019-02-01","婚礼"));
        arrayList_person.add(new Person("久飞","100","2019-03-05","婚礼"));
        arrayList_person.add(new Person("久飞","100","2019-04-01","婚礼"));
        for(Person i:People){
            Peoplenew.add(i);
        }

    }
    public static class PersonAdapter extends ArrayAdapter<Person> {
        private int resourceId;
        public PersonAdapter(Context context, int resource, List<Person> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Person person = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((TextView) view.findViewById(R.id.person_item_kongjian)).setText(person.getTitle());//显示名称
            ((TextView) view.findViewById(R.id.person_item_kongjian2)).setText(person.getMoney());//显示名称
            ((TextView) view.findViewById(R.id.person_item_kongjian3)).setText(person.getDate());//显示名称
            ((TextView) view.findViewById(R.id.person_item_kongjian4)).setText(person.getReason());//显示名称
            return view;
        }
    }
    //downFragment适配器
    public static class downAdapter extends BaseExpandableListAdapter{
        ArrayList<String> mGroupList;
        ArrayList<ArrayList<Person>> mChildList;
        public downAdapter(ArrayList<String> groupList,ArrayList<ArrayList<Person>> childList){
            mGroupList=groupList;
            mChildList=childList;
        }
        @Override
        public int getGroupCount() {
            return mGroupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mChildList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mGroupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mChildList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition + childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group,parent,false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView)convertView.findViewById(R.id.textgroup);
                convertView.setTag(groupViewHolder);
            }else {
                groupViewHolder = (GroupViewHolder)convertView.getTag();
            }
            groupViewHolder.tvTitle.setText(mGroupList.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.tvTitle = (TextView)convertView.findViewById(R.id.person_item_kongjian);
                childViewHolder.tvTitle2 = (TextView)convertView.findViewById(R.id.person_item_kongjian2);
                childViewHolder.tvTitle3 = (TextView)convertView.findViewById(R.id.person_item_kongjian3);
                childViewHolder.tvTitle4 = (TextView)convertView.findViewById(R.id.person_item_kongjian4);
                convertView.setTag(childViewHolder);

            }else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.tvTitle.setText(mChildList.get(groupPosition).get(childPosition).getTitle());
            childViewHolder.tvTitle2.setText(mChildList.get(groupPosition).get(childPosition).getMoney());
            childViewHolder.tvTitle3.setText(mChildList.get(groupPosition).get(childPosition).getDate());
            childViewHolder.tvTitle4.setText(mChildList.get(groupPosition).get(childPosition).getReason());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    static class GroupViewHolder {
        TextView tvTitle;
    }

    static class ChildViewHolder {
        TextView tvTitle;
        TextView tvTitle2;
        TextView tvTitle3;
        TextView tvTitle4;
    }

}

