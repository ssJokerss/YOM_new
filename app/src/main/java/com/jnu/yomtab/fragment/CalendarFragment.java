package com.jnu.yomtab.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.jnu.yomtab.Activity.MainActivity;
import com.jnu.yomtab.R;
import com.jnu.yomtab.data.Person;

import java.util.ArrayList;
import java.util.Calendar;

import static com.jnu.yomtab.Activity.MainActivity.People;


public class CalendarFragment extends Fragment {
    public static ArrayList<ArrayList<Person>> show_list = new ArrayList<>();
    private ArrayList<String> group_list = new ArrayList<>();
    public static MainActivity.downAdapter DownAdapter_calender;

public static String time;
    public CalendarFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbarinit(toolbar);
        CalendarView calendarView = view.findViewById(R.id.calendarViewIdnew);

        if(group_list.size()==0){
            group_list.add("收礼");
            group_list.add("随礼");
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time =year + "-" +
                (month<10 ? ("0" + month) : month) + "-" + day;
        updateDate(time);
        calendarView.setOnDateChangeListener(new myclick());
        ExpandableListView expandableListView2 = view.findViewById(R.id.uppp);
        DownAdapter_calender = new MainActivity.downAdapter(group_list, show_list);
        expandableListView2.setAdapter(DownAdapter_calender);
        this.registerForContextMenu(expandableListView2);
        return view;

    }
// 筛选日期时间的判断，看字符串前7位是否相同
    public static void updateDate(String time){
        show_list.clear();
        ArrayList<Person> get_list = new ArrayList<>();
        for(Person i:People){
            if(i.getDate().equals(time))
                get_list.add(i);
        }
        ArrayList<Person> give_list = new ArrayList<>();
        for(Person i:downFragment.arrayList_person){
            if(i.getDate().equals(time))
                give_list.add(i);
        }
        show_list.add(get_list);
        show_list.add(give_list);
    }

    private void toolbarinit( Toolbar toolbar){
        //toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);//设置导航栏图标
        //toolbar.setLogo(R.drawable.ic_down);//设置app logo
        //toolbar.setTitle();//设置主标题
        //toolbar.setSubtitle("主页");//设置子标题
        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Toast.makeText(getContext(), R.string.menu_search , Toast.LENGTH_SHORT).show();
                } else if (menuItemId == R.id.action_item1) {
                    Toast.makeText(getContext() , R.string.item_01 , Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(getContext() , R.string.item_02 , Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    private class myclick implements CalendarView.OnDateChangeListener {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            month++;
            if(month < 10)
                time = year + "-0" + month + "-" + dayOfMonth;
            else
                time = year + "-" + month + "-" + dayOfMonth;
        }
    }
}