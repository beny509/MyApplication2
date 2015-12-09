package com.example.administrator.myapplication2;

/**
 * Created by Administrator on 2015/9/22.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import add.util.AsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
//import add.util.CustomTitleBar;
public class LoginActivity  extends Activity {
    private AutoCompleteTextView user_atxt;// 用户名
    private EditText password_etxt;// 密码
    private TextView vis_addr_txt;//http://
    private EditText vis_addr_detail_txt;// 地址
    private String userName;
    private String userPwd;
    private String webURL;
    private boolean isNetError;
    private ProgressDialog proDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
         setContentView(R.layout.title);
         getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
        TextView textView = (TextView)  findViewById(R.id.title_vtxt);//////////
        textView.setText("test");/////////////////////////////*/

         requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);
        findViewById();
        initListener() ;


    }

    private void findViewById()
    {
        SharedPreferences PSetting=getSharedPreferences("Setting", MODE_PRIVATE) ;
        webURL= PSetting.getString("webURL","http://183.233.174.11:10005") ;
        vis_addr_detail_txt=(EditText)findViewById(R.id.vis_addr_detail_txt);

        vis_addr_detail_txt.setText(webURL.replace("http://","").trim());

        userName= PSetting.getString("userName","admin") ;
        userPwd=PSetting.getString("userPwd","changhe123") ;
        user_atxt=(AutoCompleteTextView)findViewById(R.id.user_atxt);
        user_atxt.setText(userName) ;
        password_etxt=(EditText)findViewById(R.id.password_etxt);
        password_etxt.setText(userPwd);

     //  String [] arr={"admin","chadmin","aac"};
      // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item, arr);
      //  user_atxt.setAdapter(arrayAdapter);
        Thread NameThread = new Thread(new NameRunHandler());
        NameThread.start();



    }
    Handler loginHand=new Handler()
    {
        public void  handleMessage(Message msg)
        {
            isNetError=msg.getData().getBoolean("isNetError");
            if (proDialog != null) {
                proDialog.dismiss();
            }
            if(isNetError)
            {Toast.makeText(LoginActivity.this,"当前网络不可以用!!!",Toast.LENGTH_SHORT).show();}
            else
            {Toast.makeText(LoginActivity.this,"错误的用户名或密码!!!",Toast.LENGTH_SHORT).show();}
        }

    };
    Handler anymsgHand=new Handler()
    {
        public void  handleMessage(Message msg)
        {
            String MsgGet=msg.getData().getString("StrMsg");
            Toast.makeText(LoginActivity.this,MsgGet,Toast.LENGTH_SHORT).show();

        }

    };
    Handler jsonHand=new Handler()
    {
        public void  handleMessage(Message msg)
        {
            String MsgGet=msg.getData().getString("JsonMsg");

            get_arr_toa(MsgGet);

        }

    };
    private boolean getLocalLogin(String userName, String password,  String validateUrl) {
        boolean loginState = false;
       // StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());//2015-9-29 加
        String res_str = new AsyncHttpClient().get(validateUrl, isNetError);

        if (res_str.equals("1")) {
            loginState = true;
        }
        return loginState;
    }
private void save_login_info()
{
    SharedPreferences preferences = getSharedPreferences( "Setting", MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("userName", user_atxt.getText().toString());
    editor.putString("userPwd", password_etxt.getText().toString());

    editor.putString("webURL", "http://" + vis_addr_detail_txt.getText().toString().trim());
    editor.commit();
}
    private void initListener(  ) {
        Button sign_in = (Button) findViewById(R.id.sign_in_btn);
        Button set_addr = (Button) findViewById(R.id.set_addr_btn);
        Button back_value = (Button) findViewById(R.id.back_value_btn);
        sign_in.setFocusable(true);
        sign_in.setFocusableInTouchMode(true);
        sign_in.requestFocus();
        sign_in.requestFocusFromTouch();
        View.OnClickListener ocl = new View.OnClickListener() {
            public void onClick(View v) {
               // Intent intent = new Intent(LoginActivity.this, MusicService.class);
                switch (v.getId()) {
                    case R.id.sign_in_btn: // 登入
                        userName = user_atxt.getText().toString().trim();
                        userPwd = password_etxt.getText().toString().trim();
                        if ("".equals(userName)) {
                            Toast.makeText(LoginActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT)
                                    .show();
                        } else if ("".equals(userPwd)) {
                            Toast.makeText(LoginActivity.this, "密码不能为空!", Toast.LENGTH_SHORT)
                                    .show();
                        } else if ("".equals(webURL)) {
                            Toast.makeText(LoginActivity.this, "请先填写服务器!", Toast.LENGTH_SHORT)
                                    .show();
                            Log.i("webURL---------------",webURL);
                        } else {
                             proDialog= new ProgressDialog(LoginActivity.this);
                            proDialog.getWindow().setGravity(Gravity.BOTTOM);
                             proDialog.show();
                            Thread loginThread = new Thread(new LoginRunHandler());
                            loginThread.start();

                        }
                        break;
                    case R.id.set_addr_btn: // 设置
                        save_login_info();
                        Toast.makeText(LoginActivity.this, "已保存.", Toast.LENGTH_SHORT).show();
                        findViewById();//保存完成后，重新读取 并通过Atxt顺带检测网络是否可用
                        break;
                    case R.id.back_value_btn: // 还原
                      //  webURL="http://183.233.174.11:10005";
                      //  vis_addr_detail_txt.setText(webURL.replace("http://",""));
                        findViewById();
                        break;
                }
            }
        };

        sign_in.setOnClickListener(ocl);
        set_addr.setOnClickListener(ocl);
        back_value.setOnClickListener(ocl);
    }
    class LoginRunHandler implements Runnable
    {
       @Override
        public void run() {
            String validateURL = webURL + "/Login/ckLogin?username=" + userName
                    + "&pwd=" + userPwd;
            boolean loginState = getLocalLogin(userName, userPwd, validateURL);
           //Toast.makeText(LoginActivity.this,String.valueOf(loginState), Toast.LENGTH_SHORT).show();
                   if(loginState)
                   {
                       // 全部正确跳转到主界面界面
                       Intent intent=new Intent();
                       Bundle bundle=new Bundle();
                       bundle.putString("userName",userName);
                       bundle.putString("userPwd",userPwd);
                       intent.putExtras(bundle);
                       save_login_info();
                       intent.setClass(LoginActivity.this, MainActivity.class);
                       startActivity(intent);
                   }
                   else
                   {
                       Message message = new Message();
                       Bundle bundle = new Bundle();
                       bundle.putBoolean("isNetError", isNetError);
                       message.setData(bundle);
                       loginHand.sendMessage(message);
                   }
        }
    };
    public void get_arr_toa(String res_str)
    {
        if(!res_str.isEmpty() && !res_str.trim().equals("")) {
            try {
                user_atxt = (AutoCompleteTextView) findViewById(R.id.user_atxt);
                JSONObject jsobObj = new JSONObject(res_str);
                JSONArray json_arr = jsobObj.getJSONArray("User_Info_web_list");
                // JSONObject json_arr = jsobObj.getJSONObject("User_Info_web_list");
                List<String> str_list = new ArrayList();
                for (int i = 0; i < json_arr.length(); i++) {
                    String b = json_arr.getJSONObject(i).getString("WorkNum");
                    str_list.add(b);
                }

                String[] arr = str_list.toArray(new String[str_list.size()]);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, arr);
                user_atxt.setAdapter(arrayAdapter);
               // Toast.makeText(LoginActivity.this, "用户名加载成功.", Toast.LENGTH_SHORT).show();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("StrMsg", "用户名加载成功.");
                message.setData(bundle );
                anymsgHand.sendMessage(message);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
               // Toast.makeText(LoginActivity.this, "用户名加载失败!!!", Toast.LENGTH_SHORT).show();
                // e.printStackTrace();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("StrMsg", "用户名加载失败!!!");
                message.setData(bundle );
                anymsgHand.sendMessage(message);
            }
        }
        else
          {// Toast.makeText(LoginActivity.this, "用户名加载找不到接口!!!", Toast.LENGTH_SHORT).show();
              Message message = new Message();
              Bundle bundle = new Bundle();
              bundle.putString("StrMsg", "用户名加载找不到接口!!!");
              message.setData(bundle );
              anymsgHand.sendMessage(message);
          }
    }
    class NameRunHandler implements Runnable
    {
        @Override
        public void run() {
            try {
                String validateURL = webURL + "/Login/GetUsers";
                String res_str = new AsyncHttpClient().get(validateURL, isNetError);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("JsonMsg", res_str);
                message.setData(bundle );
                jsonHand.sendMessage(message);
            }
            catch (Exception e)
            {//Toast.makeText(LoginActivity.this, "网络故障!!!", Toast.LENGTH_SHORT).show();

                Message message = new Message();
                Bundle bundle = new Bundle();
             bundle.putString("StrMsg", "网络故障!!!");
                message.setData(bundle );
                anymsgHand.sendMessage(message);
             }
        }
    };
}
