package com.jnu.yomtab.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jnu.yomtab.R;
import com.jnu.yomtab.fragment.CalendarFragment;

import java.util.Calendar;

public class ButtonMainActivity extends AppCompatActivity {
    private Button startButtonActivity, buttonCancel;
    private EditText editName;
    private EditText editMoney;
    private EditText editDate;
    private EditText mEditText;
    private int insertPosition;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_main);
        CalendarGet();
        Spinner spinner_relation = (Spinner) findViewById(R.id.spinner_relation_edit);
        final Spinner spinner_zhangben = (Spinner) findViewById(R.id.spinner_zhangben_edit);

        spinner_zhangben.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                str = (String)spinner_zhangben.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        startButtonActivity = (Button) findViewById(R.id.button_save);
        editName = (EditText) findViewById(R.id.name_edit);
        editName.setText(getIntent().getStringExtra("title"));
        editMoney = (EditText) findViewById(R.id.money_edit);

        int s = getIntent().getIntExtra("money",0);
        System.out.println(s);
        String s1 = s+"";
        editMoney.setText(s1);
        editDate= (EditText) findViewById(R.id.date_edit);
        String time = getIntent().getStringExtra("date");
        if(time==null)
            time = CalendarFragment.time;
        editDate.setText(time);

        //insertPosition = getIntent().getIntExtra("insert_position",0);
        insertPosition = 1;
        startButtonActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ButtonMainActivity.this,MainActivity.class);
                intent.putExtra("title", editName.getText().toString());
                intent.putExtra("insert_position", insertPosition);
                intent.putExtra("money", editMoney.getText().toString());
                intent.putExtra("date", editDate.getText().toString());
                intent.putExtra("reason", str);
                setResult(RESULT_OK, intent);
                ButtonMainActivity.this.finish();
            }
        });
        //亲人类型
        spinner_relation.setDropDownWidth(400); //下拉宽度
        spinner_relation.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner_relation.setDropDownVerticalOffset(100); //下拉的纵向偏
        String[] spinnerItems = {"亲人","朋友","同事","其他"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ButtonMainActivity.this,
                R.layout.item_select, spinnerItems);
        //自定义下拉的字体样式
        spinnerAdapter.setDropDownViewResource(R.layout.item_drop);
        //这个在不同的Theme下，显示的效果是不同的
        //spinnerAdapter.setDropDownViewTheme(Resources.Theme.LIGHT);
        spinner_relation.setAdapter(spinnerAdapter);
        //账本类型
        spinner_zhangben.setDropDownWidth(400); //下拉宽度
        spinner_zhangben.setDropDownHorizontalOffset(100); //下拉的横向偏移
        spinner_zhangben.setDropDownVerticalOffset(100); //下拉的纵向偏
        String[] spinnerItems2 = {"婚礼","压岁","生日","其他"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(ButtonMainActivity.this,
                R.layout.item_select, spinnerItems2);
        //自定义下拉的字体样式
        spinnerAdapter.setDropDownViewResource(R.layout.item_drop);
        //这个在不同的Theme下，显示的效果是不同的
        //spinnerAdapter.setDropDownViewTheme(Resources.Theme.LIGHT);
        spinner_zhangben.setAdapter(spinnerAdapter2);
    }
    protected void CalendarGet(){
        //日历
        mEditText = (EditText)findViewById(R.id.date_edit);//为什么为null
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ButtonMainActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String time;
                monthOfYear++;
                if(monthOfYear < 10)
                    time = year + "-0" + monthOfYear + "-" + dayOfMonth;
                else
                    time = year + "-" + monthOfYear+ "-" + dayOfMonth;
                ButtonMainActivity.this.mEditText.setText(time);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}