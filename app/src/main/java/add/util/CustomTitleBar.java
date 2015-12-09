package add.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.administrator.myapplication2.R;//2015-10-21



public class CustomTitleBar {

    private static Activity mActivity;

    private static MyInterface mMyI;

    public static  void getTitleBar(Activity activity,String title,boolean BackVisBool ,boolean ListVisBool,MyInterface myI ) {
        mActivity = activity;

        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        activity.setContentView(R.layout.title);
       activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.title);
        TextView textView = (TextView) activity.findViewById(R.id.title_vtxt);//////////
        textView.setText(title);/////////////////////////////
        ImageButton titleBackBtn = (ImageButton) activity.findViewById(R.id.btn_back0);
        ImageButton titleListBtn = (ImageButton) activity.findViewById(R.id.btn_list0);
        if(BackVisBool)
        {
            titleBackBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            titleBackBtn.setVisibility(View.INVISIBLE);
        }
        if(ListVisBool)
        {
            titleListBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            titleListBtn.setVisibility(View.INVISIBLE);
        }
        titleBackBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
            }
        });
        mMyI=myI;
        titleListBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mMyI.MyAction();
//未添加
            }
        });
    }

        public interface MyInterface
        {

            public void MyAction( );
        }


}


