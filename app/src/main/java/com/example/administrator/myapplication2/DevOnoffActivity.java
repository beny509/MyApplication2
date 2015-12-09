package com.example.administrator.myapplication2;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DevOnoffActivity extends AppCompatActivity {
    private String userName;
    private String userPwd;
    private String webURL;
    private boolean isNetError;
    private String onOffMode="正常模式";
    private TextView title_vtxt;//未用
    private String selNode;

    private Spinner mySpinner;
    private ArrayAdapter<String> adapter_sp;
    private List<String> list_sp = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCustomActionBar();
        setContentView(R.layout.activity_dev_onoff);
        init_spinner();
    }
    private void findViewById() {
        SharedPreferences PSetting = getSharedPreferences("Setting", MODE_PRIVATE);
        webURL = PSetting.getString("webURL", "http://183.233.174.11:10005");
        userName = PSetting.getString("userName", "");
        userPwd=PSetting.getString("userPwd","") ;
        selNode=PSetting.getString("selNode", "");
        title_vtxt.setText(selNode);
    }

    private boolean initCustomActionBar() {
        ActionBar mActionbar = getSupportActionBar();
        if (mActionbar == null) {
            return false;
        }
        mActionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);
          mActionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_blue));
        mActionbar.setCustomView(R.layout.title);

        initListener();
       /* ;*/
        return true;
    }
    private void initListener(  ) {
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back0);
        ImageButton btn_list = (ImageButton) findViewById(R.id.btn_list0);
        title_vtxt=(TextView) findViewById(R.id.title_vtxt);
        btn_list.setVisibility(View.INVISIBLE);
        View.OnClickListener ocl = new View.OnClickListener() {
            public void onClick(View v) {
                // Intent intent = new Intent(LoginActivity.this, MusicService.class);
                switch (v.getId()) {
                    case R.id.btn_back0: // 返回
                        //KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
                        //onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
                        DevOnoffActivity.this.finish();
                        break;
                    case R.id.btn_list0: // 弹出
                        Toast.makeText(DevOnoffActivity.this, "测试!", Toast.LENGTH_SHORT)
                                .show();
                        break;

                }
            }
        };

        btn_back.setOnClickListener(ocl);
        btn_list.setOnClickListener(ocl);

    }

    private void init_spinner()
    {
        //region spinner
        // 第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list_sp.add("正常模式");
        list_sp.add("应急模式");

        // myTextView = (TextView)findViewById(R.id.TextView_city);
        mySpinner = (Spinner)findViewById(R.id.spMain);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter_sp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list_sp);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。//simple_spinner_dropdown_item
        adapter_sp.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //第四步：将适配器添加到下拉列表上
        mySpinner.setAdapter(adapter_sp);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                //  myTextView.setText("您选择的是："+ adapter_sp.getItem(arg2));
                /* 将mySpinner 显示*/
                onOffMode= adapter_sp.getItem(arg2);

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                // myTextView.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }

        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
        //endregion
    }
}
