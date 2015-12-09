package com.example.administrator.myapplication2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.animation.RotateAnimation;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import add.util.AsyncHttpClient;
import add.util.ButtonTextImg;
 import android.support.v7.app.ActionBar;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MusicService";
    private String userName;
    private String userPwd;
    private String webURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();
        initCustomActionBar();
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "成成的MusicServiceActivity", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "梁工的MusicServiceActivity");
        initlizeViews();

    }
    private boolean initCustomActionBar() {
        ActionBar  mActionbar = getSupportActionBar();
        if (mActionbar == null) {
            return false;
        }
        mActionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionbar.setDisplayShowHomeEnabled(false);
        mActionbar.setDisplayShowCustomEnabled(true);
        //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       //    View view = inflater.inflate(R.layout.title,  new LinearLayout(mActionbar.getThemedContext()), false);

       mActionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_blue));
        mActionbar.setCustomView(R.layout.title);
       // mActionbar.setCustomView(view,new ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //mActionbar.getCustomView();
        initListener();
       /* ;*/
        return true;
    }

    private void initListener(  ) {
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back0);
        ImageButton btn_list = (ImageButton) findViewById(R.id.btn_list0);
        btn_list.setVisibility(View.INVISIBLE);
        View.OnClickListener ocl = new View.OnClickListener() {
            public void onClick(View v) {
                // Intent intent = new Intent(LoginActivity.this, MusicService.class);
                switch (v.getId()) {
                    case R.id.btn_back0: // 返回
                        //KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
                        //onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
                        MainActivity.this.finish();
                        break;
                    case R.id.btn_list0: // 弹出
                        Toast.makeText(MainActivity.this, "测试!", Toast.LENGTH_SHORT)
                                .show();
                        break;

                }
            }
        };

        btn_back.setOnClickListener(ocl);
        btn_list.setOnClickListener(ocl);

    }
    /**
     * 透明效果
     * @return
     */
    public Animation getAlphaAnimation() {

        //Animation animation = new AlphaAnimation(1.0f, 0.5f);
        //设置动画插值器 被用来修饰动画效果,定义动画的变化率

        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画插值器 被用来修饰动画效果,定义动画的变化率
        animation.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间

        animation.setDuration(2000);
        return animation;
    }
    private void initlizeViews() {
        Button  btnStart = (Button) findViewById(R.id.startMusic);
        Button btnStop = (Button) findViewById(R.id.stopMusic);
        ButtonTextImg btndevmap = (ButtonTextImg) findViewById(R.id.btn_devmap );
        ButtonTextImg btnonoff = (ButtonTextImg) findViewById(R.id.btn_onoff );
        OnClickListener ocl = new OnClickListener() {
            public void onClick(View v) {
                Animation anim = getAlphaAnimation();
                v.startAnimation(anim);
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                switch (v.getId()) {
                    case R.id.startMusic: // 开始服务
                        startService(intent);
                        break;
                    case R.id.stopMusic: // 停止服务
                        stopService(intent);
                        break;
                    case R.id.btn_devmap: // test
                        break;
                    case R.id.btn_onoff:
                        set_setting_info("集中开关","gotoPart");
                        Thread loginThread = new Thread(new exlistviewRunHandler());
                        loginThread.start();
                        break;

                }
            }
        };

         btnStart.setOnClickListener(ocl);
        btnStop.setOnClickListener(ocl);
        btndevmap.setOnClickListener(ocl);
        btnonoff.setOnClickListener(ocl);
    }


    public    void set_setting_info(final String partName,final String key_str)
    {
        SharedPreferences preferences = getSharedPreferences( "Setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key_str,partName.trim());

        editor.commit();
    }


            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        // initlizeViews();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class exlistviewRunHandler implements Runnable
    {
        @Override
        public void run() {


            //  跳转到 界面
            Intent intent=new Intent();
            // Bundle bundle=new Bundle();
            //  bundle.putString("userName",userName);
            //  bundle.putString("userPwd",userPwd);
            // intent.putExtras(bundle);
            intent.setClass(MainActivity.this, Dev_exlistviewActivity.class);
            startActivity(intent);

        }
    };
}
