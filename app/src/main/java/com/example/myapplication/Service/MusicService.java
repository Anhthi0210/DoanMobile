package com.example.myapplication.Service;

import static com.example.myapplication.Activity.PlayerActivity.listSongs;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.DAO.MusicFiles;
import com.example.myapplication.Interface.ActionPlaying;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    //Sử dụng IBinder giúp tạo kết nối giữa Activity và dịch vụ một cách an toàn và dễ dàng.
    // Nó giúp tách biệt hoạt động của dịch vụ và giao diện người dùng,
    // giúp ứng dụng của bạn trở nên linh hoạt và dễ bảo trì.
    IBinder myBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    Uri uri;
    int position = -1;
    ActionPlaying actionPlaying;
    ArrayList<MusicFiles> musicFiles = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        musicFiles = listSongs;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind", "Method");
        return myBinder;
    }


    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myPosition = intent.getIntExtra("servicePosition", -1);
        String actionName = intent.getStringExtra("ActionName");
        if (myPosition != -1){
            createMediaPlayer(myPosition);
            playMedia(myPosition);
        }
        if(actionName != null){
            switch (actionName){
                case "playPause":
                    Toast.makeText(this, "PlayPause", Toast.LENGTH_SHORT).show();
                    if (actionPlaying != null){
                        Log.e("Inside", "Action");
                        actionPlaying.playPauseBtnClicked();
                    }
                    break;
                case "next":
                    Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                    if (actionPlaying != null){
                        Log.e("Inside", "Action");
                        actionPlaying.nextBtnBtnClicked();
                    }
                    break;
                case "previous":
                    Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                    if (actionPlaying != null){
                        Log.e("Inside", "Action");
                        actionPlaying.prevBtnClicked();
                    }
                    break;
            }
        }
        return START_STICKY;
    }


    private void playMedia(int StartPosition) {
        musicFiles = listSongs;
        position = StartPosition;
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(musicFiles != null){
                createMediaPlayer(position);
                mediaPlayer.start();
            }
        }
        else {
            createMediaPlayer(position);
            mediaPlayer.start();
        }
    }

    public void start(){
        mediaPlayer.start();

    }
    public boolean isPlaying(){
            return mediaPlayer.isPlaying();
    }
    public void stop(){
        mediaPlayer.stop();
    }
    public void release(){
        mediaPlayer.release();
    }
    public int getDuration(){
        return mediaPlayer.getDuration();
    }
    public void seekTo(int position){
        mediaPlayer.seekTo(position);
    }
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    public void createMediaPlayer(int position){
        uri = Uri.parse(musicFiles.get(position).getPath());
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public void OnCompleted(){
        mediaPlayer.setOnCompletionListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (actionPlaying != null){
            actionPlaying.nextBtnBtnClicked();
            mediaPlayer.start();
        }

    }
    public void setCallBack(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;
    }
}

