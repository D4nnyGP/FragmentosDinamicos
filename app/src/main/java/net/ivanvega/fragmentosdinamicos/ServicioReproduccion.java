package net.ivanvega.fragmentosdinamicos;

import static net.ivanvega.fragmentosdinamicos.App.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;

public class ServicioReproduccion extends Service {

    MediaPlayer mediaPlayer;
    private static int ID_NOT = 1234;
    NotificationCompat.Builder notificacion;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "iniciando servicio", Toast.LENGTH_SHORT).show();
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,
                CHANNEL_ID).setContentTitle("Example Service ").setContentText("Reproducciondo Audio")
                .setContentIntent(pendingIntent).build();



        //notificacion = new NotificationCompat.Builder(this);

        //return START_STICKY;
       return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "terminando servicio", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        super.onDestroy();
    }
}
