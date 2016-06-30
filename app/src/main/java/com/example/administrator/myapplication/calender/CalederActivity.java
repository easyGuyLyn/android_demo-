package com.example.administrator.myapplication.calender;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import utils.CommonUtils;

public class CalederActivity extends Activity {
    private Dialog dialog;
    private String date = null;// 设置默认选中的日期  格式为 “2014-04-05” 标准DATE格式
    private TextView popupwindow_calendar_month;
    private KCalendar calendar;
    private List<String> list = new ArrayList<String>(); //设置标记列表

    private String date1 = null;
    private TextView popupwindow_calendar_month1;
    private KCalendar calendar1;
    private List<String> list1 = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calender);
        popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
        calendar = (KCalendar) findViewById(R.id.popupwindow_calendar);
        popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
                + calendar.getCalendarMonth() + "月");
        if (null != date) {

            int years = Integer.parseInt(date.substring(0,
                    date.indexOf("-")));
            int month = Integer.parseInt(date.substring(
                    date.indexOf("-") + 1, date.lastIndexOf("-")));
            popupwindow_calendar_month.setText(years + "年" + month + "月");

            calendar.showCalendar(years, month);
            calendar.setCalendarDayBgColor(date,
                    R.drawable.calendar_date_focused);
        }


        list.add("2015-05-01");
        list.add("2015-05-02");
        calendar.addMarks(list, 0);

        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    list.add(dateFormat);
                    calendar.addMarks(list, 0);
                    calendar.removeAllBgColor();
                    calendar.setCalendarDayBgColor(dateFormat,
                            R.drawable.calendar_date_focused);
                    date = dateFormat;//最后返回给全局 date
                }
            }
        });

        //监听当前月份
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month
                        .setText(year + "年" + month + "月");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCalenderDialog();
    }

    private void showCalenderDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dia_sign_in, null);
        ImageView iv_cancle = (ImageView) view.findViewById(R.id.iv_cancle);
        popupwindow_calendar_month1 = (TextView) view.findViewById(R.id.popupwindow_calendar_month_dia);
        calendar1 = (KCalendar) view.findViewById(R.id.popupwindow_calendar_dia);
        popupwindow_calendar_month1.setText(calendar1.getCalendarYear() + "年"
                + calendar1.getCalendarMonth() + "月");
        dialog = CommonUtils.getDialog(this, view, Gravity.CENTER, false);
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (null != date1) {

            int years = Integer.parseInt(date1.substring(0,
                    date1.indexOf("-")));
            int month = Integer.parseInt(date1.substring(
                    date1.indexOf("-") + 1, date1.lastIndexOf("-")));
            popupwindow_calendar_month1.setText(years + "年" + month + "月");

            calendar1.showCalendar(years, month);
            calendar1.setCalendarDayBgColor(date1,
                    R.drawable.calendar_date_focused);
        }

        list1.add("2015-05-01");
        list1.add("2015-05-02");
        calendar1.addMarks(list1, 0);

        //监听所选中的日期
        calendar1.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(
                        dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar1.getCalendarMonth() - month == 1//跨年跳转
                        || calendar1.getCalendarMonth() - month == -11) {
                    calendar1.lastMonth();

                } else if (month - calendar1.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar1.getCalendarMonth() == -11) {
                    calendar1.nextMonth();

                } else {
                    list1.add(dateFormat);
                    calendar1.addMarks(list1, 0);
                    calendar1.removeAllBgColor();
                    calendar1.setCalendarDayBgColor(dateFormat,
                            R.drawable.calendar_date_focused);
                    date1 = dateFormat;//最后返回给全局 date
                }
            }
        });

        //监听当前月份
        calendar1.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month1
                        .setText(year + "年" + month + "月");
            }
        });
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
