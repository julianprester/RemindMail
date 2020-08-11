package me.prester.remindmail.backend;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

import me.prester.remindmail.R;
import me.prester.remindmail.main.MainActivity;
import me.prester.remindmail.util.Constants;
import me.prester.remindmail.util.InjectorUtils;

public class NotificationHelper {

    private static NotificationHelper instance;
    private PreferencesHelper mPreferencesHelper;

    private NotificationHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    public void createNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        String recipient = mPreferencesHelper.getRecipient(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.notification_content_title) + recipient);

        Intent respondIntent = new Intent(context, RemindMailReceiver.class);
        respondIntent.setAction(Constants.ACTION_SEND_EMAIL_INLINE);
        PendingIntent respondPendingIntent = PendingIntent.getBroadcast(context, 0, respondIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder(Constants.KEY_INLINE_REPLY)
                .setLabel(context.getString(R.string.notification_reply_text))
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_notification, context.getString(R.string.notification_reply_button), respondPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(Constants.ACTION_SEND_EMAIL);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        builder.addAction(action);
        builder.setShowWhen(false);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setContentIntent(pendingContentIntent);
        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        builder.setContentText(context.getString(R.string.notification_content_text));
        notificationManager.notify(Constants.NOTIFICATION_INLINE_ACTION, builder.build());
    }

    public void cancel(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(Constants.NOTIFICATION_INLINE_ACTION);
    }

    public void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, context.getString(R.string.channel_name), NotificationManager.IMPORTANCE_MIN);
            channel.setDescription(context.getString(R.string.channel_description));
            channel.setShowBadge(false);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static synchronized NotificationHelper getInstance() {
        if(instance == null) {
            instance = new NotificationHelper(InjectorUtils.getPreferencesHelper());
        }
        return instance;
    }
}
