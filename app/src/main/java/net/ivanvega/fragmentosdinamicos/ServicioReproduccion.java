package net.ivanvega.fragmentosdinamicos;

import static net.ivanvega.fragmentosdinamicos.App.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
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
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.nio.channels.Channel;

public class ServicioReproduccion extends Service implements MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl,
        View.OnTouchListener {

    private static final String CHANNEL_ID = "AUDIOLIBROS";

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
        String titulo;
        String contenido;
        uri=intent.getStringExtra("uri");
        contenido = "Reproduciendo: "+uri;
        titulo = intent.getStringExtra("titulo");
        Toast.makeText(this, "uri: "+uri, Toast.LENGTH_SHORT).show();

        //ReceiverAlarm.createNotificationChannel(this,new Intent(this,MainActivity.class));
        mostrarNotificacion(this, null,titulo,contenido);

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

        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
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







        //mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();

        return START_NOT_STICKY;
    }

    private void mostrarNotificacion(Context context, Intent intent, String titulo, String contenido) {
        //createNotificationChannel(context,intent);

        Intent intentAct = new Intent(this,MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent
                .getActivity(this, 1, intentAct, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_book_24)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1001, builder.build());



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
