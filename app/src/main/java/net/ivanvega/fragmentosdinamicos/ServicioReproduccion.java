package net.ivanvega.fragmentosdinamicos;

import static net.ivanvega.fragmentosdinamicos.App.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.nio.channels.Channel;

public class ServicioReproduccion extends Service implements MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl,
        View.OnTouchListener {

    MediaPlayer mediaPlayer;
    MediaController mediaController;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uri="";
        uri=intent.getStringExtra("uri");
        Toast.makeText(this, "uri: "+uri, Toast.LENGTH_SHORT).show();
        if( mediaPlayer== null){
            mediaPlayer = new MediaPlayer();
            mediaController = new MediaController(this);
            mediaPlayer.setOnPreparedListener(this);
            try {
                mediaPlayer.setDataSource(this,
                        Uri.parse(uri));
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,
                CHANNEL_ID).setContentTitle("Example Service ").setContentText("Reproducciondo Audio")
                .setContentIntent(pendingIntent).build();



        //mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "terminando servicio", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setEnabled(true);
        mediaController.setPadding(0,0,0,110);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mediaController.show();
        return false;
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }



    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


}
