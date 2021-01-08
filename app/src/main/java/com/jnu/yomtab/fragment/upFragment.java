package com.jnu.yomtab.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jnu.yomtab.Activity.ButtonMainActivity;
import com.jnu.yomtab.Activity.MainActivity;
import com.jnu.yomtab.Activity.PieChartActivity;
import com.jnu.yomtab.R;
import com.jnu.yomtab.data.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.jnu.yomtab.Activity.MainActivity.People;

public class upFragment extends Fragment {
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_ADDNEW = CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_ADDNEW+1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK= 902;
    public static List<Person> Peoplenew = new ArrayList<>();
    private MainActivity.PersonAdapter personAdapter;
    private Button TongjiButton;

    public upFragment(MainActivity.PersonAdapter personAdapter){
        this.personAdapter = personAdapter;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public upFragment() {
        // Required empty public constructor
    }

    public static upFragment newInstance(String param1, String param2) {
        upFragment fragment = new upFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this.getContext());
        //button的导航栏
        //upp Listview
        if(v == this.getActivity().findViewById(R.id.upp)) {
            //获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            //menu.setHeaderTitle(People.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            //用const 将常数变为宏变量 更具连贯性
            menu.add(1, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(1, CONTEXT_MENU_ADDNEW, 0, "新建");
            menu.add(1, CONTEXT_MENU_UPDATE, 0, "修改");
            menu.add(1, CONTEXT_MENU_ABOUT, 0, "关于...");
        }

    }
    //公用的菜单选项
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getGroupId()!=1)//久飞牛逼
            return false;
       AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position=menuInfo.position;
        switch (item.getItemId()) {

            case CONTEXT_MENU_DELETE :
                //引入这个对话框的包
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("询问");
                builder.setMessage("你确定要删除\""+ People.get(position).getTitle()+ "\"？");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        People.remove(position);
                        personAdapter.notifyDataSetChanged();//数据更新
                    }
                });  //正面的按钮（肯定）
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    //点击响应函数
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }); //反面的按钮（否定)
                builder.create().show();
                break;

            case CONTEXT_MENU_ADDNEW:
                Intent intent = new Intent(getActivity(),ButtonMainActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_BOOK);
                break;
            case CONTEXT_MENU_UPDATE:{
                int Position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                Intent intent2 = new Intent(getActivity(),ButtonMainActivity.class);
                intent2.putExtra("title",Peoplenew.get(Position).getTitle());
                intent2.putExtra("insert_position",Position);
                startActivityForResult(intent2,REQUEST_CODE_UPDATE_BOOK);

            }
            break;
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(getActivity(),"更多敬请期待",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void  toolbarinit( Toolbar toolbar){
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_NEW_BOOK:
                if (resultCode == RESULT_OK){
                    String title = data.getStringExtra("title");
                    int insertPosition=data.getIntExtra("insert_position",0);
                    Peoplenew.add(insertPosition,new Person("博文","100","2019-01-01","婚礼"));
                    personAdapter.notifyDataSetChanged();//通知数据已经改变
                }
                break;
            case REQUEST_CODE_UPDATE_BOOK:
                if (resultCode == RESULT_OK){
                    int insertPosition=data.getIntExtra("insert_position",0);
                    Person bookAtPostion = Peoplenew.get(insertPosition);
                    bookAtPostion.setTitle(data.getStringExtra("title"));
                    bookAtPostion.setMoney(data.getStringExtra("money"));
                    bookAtPostion.setReason(data.getStringExtra("reason"));
                    bookAtPostion.setDate(data.getStringExtra("date"));
                    personAdapter.notifyDataSetChanged();//通知数据已经改变
                    CalendarFragment.updateDate(data.getStringExtra("date"));//初始值);
                    CalendarFragment.DownAdapter_calender.notifyDataSetChanged();
                }
                break;
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_up, container, false);
        final ListView ListViewPeople = view.findViewById(R.id.upp);
        ListViewPeople.setAdapter(personAdapter);//部署适配器
        this.registerForContextMenu(ListViewPeople);//为ListView添加场景菜单生成者和响应
        final Spinner spinner_zhangben = (Spinner) view.findViewById(R.id.spinner_up);

        spinner_zhangben.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //拿到被选择项的值 核心算法
                String str = (String) spinner_zhangben.getSelectedItem();
                People.clear();
                //Peoplenew收集所有条例
                for(Person i:Peoplenew){
                    People.add(i);
                }
                for (Iterator<Person> it = People.iterator(); it.hasNext();) {
                    Person val = it.next();
                    //如果和所选的账本名称不同或者没有选择“缘由”则删除
                    if ((!val.getReason().equals(str)&&(!str.equals("缘由")))) {
                        it.remove();
                    }
                }
                personAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        //账本类型
        spinner_zhangben.setDropDownWidth(400); //下拉宽度
        spinner_zhangben.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner_zhangben.setDropDownVerticalOffset(100); //下拉的纵向偏
        String[] spinnerItems2 = {"缘由","婚礼","压岁","生日","其他"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getContext(),
                R.layout.item_select, spinnerItems2);

        spinnerAdapter2.setDropDownViewResource(R.layout.item_drop);
        spinnerAdapter2.setDropDownViewResource(R.layout.item_drop);
        spinner_zhangben.setAdapter(spinnerAdapter2);

        Toolbar toolbar_up = (Toolbar)view.findViewById(R.id.toolbar_up);
        toolbarinit(toolbar_up);
        //统计图
        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        TongjiButton=view.findViewById(R.id.fragmentup_button);
        //给btn1绑定监听事件
        TongjiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(getActivity(), PieChartActivity.class);
                //启动
                startActivity(intent);
            }
        });
        return view;
    }
}