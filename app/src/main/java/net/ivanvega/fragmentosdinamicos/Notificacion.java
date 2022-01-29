package net.ivanvega.fragmentosdinamicos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notificacion  extends Aplicacion{
    public static final String CHANNEL_ID = "EjemploServicio";

    @Override
    public void onCreate() {
        super.onCreate();
        crearNotificacion();
    }

    private void crearNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel servicechanel = new NotificationChannel(CHANNEL_ID,"Servicio Ejecutando",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
        //NotificationManager manager = getSystemService(NotificationManager.class);
       //manager.createNotificationChannel(servicechanel);

    }
}
