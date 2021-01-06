package com.jnu.yomtab.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ExpandableListView;

import com.jnu.yomtab.Activity.MainActivity;
import com.jnu.yomtab.R;
import com.jnu.yomtab.data.Person;

import java.util.ArrayList;
import java.util.Collections;
//import static com.jnu.yomtab.Activity.MainActivity.DownAdapter;

public class downFragment extends Fragment {
    public static MainActivity.downAdapter DownAdapter;
    public static ArrayList<Person> People2 = new ArrayList<>();
    public  downFragment(MainActivity.downAdapter DownAdaper){
        this.DownAdapter = DownAdaper;
    }

    public static ArrayList<String> groupList = new ArrayList<>();
    public static ArrayList<ArrayList<Person>> childList = new ArrayList<>();
    public static ArrayList<Person> arrayList_person = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down, container, false);

        initDate();
        initView(view);

        return view;
    }

    public static void initDate(){

        groupList.clear();
        childList.clear();
        for(Person i:arrayList_person){
            if(!groupList.contains(i.getDate().substring(0, 7)))
                groupList.add(i.getDate().substring(0, 7));
        }

        Collections.sort(groupList);

        for(String i:groupList){
            ArrayList<Person> tempInfo = new ArrayList<>();
            for(Person j:arrayList_person){
                if(j.getDate().substring(0, 7).equals(i))
                    tempInfo.add(j);
            }
            childList.add(tempInfo);
        }
    }

    public void initView(View view){
        ExpandableListView expandableListView = view.findViewById(R.id.exListview);

        this.registerForContextMenu(expandableListView);

        //groupList.add(CalendarFragment.time);
        //groupList.add(CalendarFragment.time);
        //groupList.add(CalendarFragment.time);
        //groupList.add(CalendarFragment.time);

        DownAdapter = new MainActivity.downAdapter(groupList, childList);
        //MainActivity.downAdapter DownAdapter = new MainActivity.downAdapter(groupList,childList);
        expandableListView.setAdapter(DownAdapter);
    }
}