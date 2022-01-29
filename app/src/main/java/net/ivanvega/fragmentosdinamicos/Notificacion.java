package net.ivanvega.fragmentosdinamicos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Notificacion  extends Aplicacion{
    public static final String CHANNEL_ID = "EjemploServicio";
    NotificationChannel servicechanel;
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            crearNotificacion();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void crearNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            servicechanel = new NotificationChannel(CHANNEL_ID, "Servicio Ejecutando",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(servicechanel);
        }

    }
}
