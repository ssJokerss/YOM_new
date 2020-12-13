package com.jnu.yomtab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarViewActivity extends Activity {

    private Context context;
    private CalendarView calendarView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        context = this;
        calendarView = (CalendarView)findViewById(R.id.calendarViewId);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String content = year+"-"+(month+1)+"-"+dayOfMonth;
                Toast.makeText(context, "你选择了:\n"+content, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
