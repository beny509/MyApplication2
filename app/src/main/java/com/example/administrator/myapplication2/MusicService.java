package com.example.administrator.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
public class MusicService extends Service {


    public MusicService() {
    }
    private static String TAG = "MusicService";
    private MediaPlayer mPlayer;

    @Override
//Do_You_Wanna_Go.mp3
    public void onCreate() {
        Toast.makeText(this,  "MusicSevice onCreate()",  Toast.LENGTH_SHORT)  .show();
 Log.e(TAG, "MusicSerice onCreate()");
        //Uri playUri = Uri.parse("/storage/sdcard1/Music/Mills.mp3");
  mPlayer = MediaPlayer.create(getApplicationContext(),R.drawable.do1);
        //   设置可以重复播放
  mPlayer.setLooping(true);
  super.onCreate();

    }



    @Override
  public void onStart(Intent intent, int startId) {

        Toast.makeText(this,  "MusicSevice  onStart()", Toast.LENGTH_SHORT) .show();
        Log.e(TAG, "MusicSerice onStart()");
         mPlayer.start();
        super.onStart(intent, startId);

    }
    @Override 
                public void onDestroy() {
        Toast.makeText(this, "MusicSevice onDestroy()", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "MusicSerice onDestroy()");  
                mPlayer.stop();
        super.onDestroy();     }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
