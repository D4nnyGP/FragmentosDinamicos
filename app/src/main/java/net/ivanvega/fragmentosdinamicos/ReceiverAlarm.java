package net.ivanvega.fragmentosdinamicos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReceiverAlarm extends BroadcastReceiver {

    private static final String CHANNEL_ID = "AUDIOLIBROS";

    @Override
    public void onReceive(Context context, Intent intent) {
        mostrarNotificacion(context,intent);
    }

    private void mostrarNotificacion(Context context, Intent intent) {
        //createNotificationChannel(context,intent);

        // Create an explicit intent for an Activity in your app
        //PendingIntent.FLAG_UPDATE_CURRENT

        Intent intentTap = new Intent(context, MainActivity.class);

        intentTap.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intentTap.putExtra("idTarea", 1001);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intentTap, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Titulo recordatorio")
                .setContentText("Te recuerdo tarea pendiente")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                ;


        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1001, builder.build());

    }

    public static void createNotificationChannel(Context ctx, Intent intent) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Audiolibros";
            String description = "Audiolibros en reproduccion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
