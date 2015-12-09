package com.example.administrator.myapplication2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import add.util.AsyncHttpClient;

public class Dev_exlistviewActivity extends Activity{
    private String userName;
    private String userPwd;
    private String webURL;
    private String gotoPart;
    private boolean isNetError;
    private String DevGroup="区域分组";

    private  List<String>  groupArray=new  ArrayList<String>();//组列表
    private  List<List<String>> childArray= new ArrayList<List<String>>();//子列表
    private  List<List<Integer>> logoArray= new ArrayList<List<Integer>>();//子列表

    private TextView myTextView;//未用
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter_sp;
    private List<String> list_sp = new ArrayList<String>();
    private void findViewById() {
        SharedPreferences PSetting = getSharedPreferences("Setting", MODE_PRIVATE);
        webURL = PSetting.getString("webURL", "http://183.233.174.11:10005");
        userName = PSetting.getString("userName", "");
        userPwd=PSetting.getString("userPwd","") ;
        gotoPart=PSetting.getString("gotoPart","") ;
        //Thread NameThread = new Thread(new NameRunHandler());
        //NameThread.start();
        myTextView=(TextView)findViewById(R.id.selectNode_txt);
    }
    public    void set_setting_info(final String partName,final String key_str)
    {
        SharedPreferences preferences = getSharedPreferences( "Setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key_str,partName.trim());

        editor.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dev_exlistview);
        findViewById();
        init_spinner();






}
    private void init_Expandlist(final List<String> group_head_strs,final List<List<String>> child_strs, final List<List<Integer>> child_logo_ints)
    {

        final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            //设置组视图的图片
            int[] logos = new int[] { R.drawable.boxadd,R.drawable.boxremove };


            //自己定义一个获得文字信息的方法
            TextView getTextView() {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 64);
                TextView textView = new TextView(
                        Dev_exlistviewActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                return textView;
            }


            //重写ExpandableListAdapter中的各个方法
            @Override
            public int getGroupCount() {
                // TODO Auto-generated method stub


                return group_head_strs.size();//generalsTypes.length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                // TODO Auto-generated method stub
                return group_head_strs.get(groupPosition).toString();// generalsTypes[groupPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                // TODO Auto-generated method stub
                return  groupPosition;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                // TODO Auto-generated method stub
                return child_strs.get(groupPosition).size();//generals[groupPosition].length;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return child_strs.get(groupPosition).get(childPosition);//generals[groupPosition][childPosition];
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                //String   string=groupArray.get(groupPosition);
                //return getGenericView(string);

                LinearLayout ll = new LinearLayout(
                        Dev_exlistviewActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(Dev_exlistviewActivity.this);
                logo.setImageResource(logos[0]);
                if(isExpanded )
                {
                    logo.setImageResource(logos[1]);
                }
                logo.setPadding(50, 0, 0, 0);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setTextColor(Color.BLACK);

                //textView.setText(groupArray.get(groupPosition).toString());// textView.setText(getGroup(groupPosition).toString());
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);

                return ll;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LinearLayout ll = new LinearLayout(
                        Dev_exlistviewActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView generallogo = new ImageView(
                        Dev_exlistviewActivity.this);
                generallogo .setImageResource(child_logo_ints.get(groupPosition).get(childPosition));//generallogo .setImageResource(generallogos[groupPosition][childPosition]);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40,40);//LinearLayout.LayoutParams.WRAP_CONTENT
                 generallogo.setLayoutParams(params );
                generallogo.setScaleType(ImageView.ScaleType.FIT_XY);

                ScaleAnimation animation =new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                       Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
               animation.setDuration(1000);//设置动画持续时间
                generallogo.setAnimation(animation);

                ll.addView(generallogo);
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition)
                        .toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public boolean isChildSelectable(int groupPosition,
                                             int childPosition) {
                // TODO Auto-generated method stub
                return true;
            }

        };

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.list);
        expandableListView.setAdapter(adapter);


        //设置item点击的监听器
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
String devno_str=adapter.getChild(groupPosition, childPosition).toString().trim();
                Toast.makeText(
                        Dev_exlistviewActivity.this,
                        devno_str.trim(),
                        Toast.LENGTH_SHORT).show();
                set_setting_info(devno_str.trim(), "selNode");
                switch (gotoPart) {
                    case "集中开关": // 开始服务
                        Thread runThread = new Thread(new DevOnoffRunHandler());
                        runThread.start();
                        break;


                }
                myTextView.setText(devno_str.trim());
                myTextView.setTag(0, mySpinner.getSelectedItemPosition());
                myTextView.setTag(1, groupPosition);
                myTextView.setTag(2,childPosition);
                return false;
            }
        });
    }
    private void init_spinner()
    {
        //region spinner
        // 第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        list_sp.add("区域分组");
        list_sp.add("电话分组");
        list_sp.add("自定义分组");

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
                DevGroup= adapter_sp.getItem(arg2);
                Thread NameThread = new Thread(new NameRunHandler());
                NameThread.start();
                arg0.setVisibility(View.VISIBLE);
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
    Handler anymsgHand=new Handler()
    {
        public void  handleMessage(Message msg)
        {
            String MsgGet=msg.getData().getString("StrMsg");
            Toast.makeText(Dev_exlistviewActivity.this,MsgGet,Toast.LENGTH_SHORT).show();

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
    public void get_arr_toa(String res_str)
    {
        if(!res_str.isEmpty() && !res_str.trim().equals("")) {
            try {

                JSONObject jsobObj = new JSONObject(res_str);
                JSONArray json_arr = jsobObj.getJSONArray("bigtree");
                JSONArray json_detail_arr = jsobObj.getJSONArray("smalltree");
                JSONArray json_linktree_arr = jsobObj.getJSONArray("linktree");
                groupArray.clear();
                childArray.clear();
                logoArray.clear();
                for (int i = 0; i < json_arr.length(); i++) {
                     json_detail_arr.getJSONArray(i) ;

                    List<String>  cilist=new  ArrayList<String>();

                    List<Integer> linklist= new  ArrayList<Integer>();
                    for (int ci = 0; ci < json_detail_arr.getJSONArray(i).length(); ci++) {
                        String temp=json_detail_arr.getJSONArray(i).getString(ci).trim();
                        if(!temp.isEmpty()) {
                            cilist.add(temp);
                        }
                        if(json_linktree_arr.getJSONArray(i).getString(ci).trim().equals("connet")) {
                            linklist.add(R.drawable.right);
                        }
                        else
                        {
                            linklist.add(R.drawable.close3_dis);
                        }
                    }
                    String b = json_arr.getString(i);//.getJSONObject(i).getString("WorkNum");
if(cilist.size()>0) {
    childArray.add(cilist);
    logoArray.add(linklist);
    groupArray.add(b);
}
                }
                if( groupArray.size()>0) {
                   init_Expandlist(groupArray,childArray,logoArray);


                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("StrMsg", "分组名加载成功.");
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
                String validateURL = webURL + "/Tree/DevInfoGroup?DevGroup="+ URLEncoder.encode(DevGroup, "utf-8").trim()+"&log_name=" + userName
                        + "&log_pass=" + userPwd + "&sn_node_mode=1";
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

    class DevOnoffRunHandler implements Runnable
    {
        @Override
        public void run() {

            Intent intent=new Intent();
            intent.setClass(Dev_exlistviewActivity.this, DevOnoffActivity.class);
            startActivity(intent);

        }
    };

}