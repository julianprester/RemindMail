package me.prester.remindmail.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.RemoteInput;

import me.prester.remindmail.main.MainActivity;
import me.prester.remindmail.util.Constants;
import me.prester.remindmail.util.InjectorUtils;

public class RemindMailReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository = InjectorUtils.getRepository();
        if(intent.getAction() != null && intent.getAction().equals(Constants.ACTION_SEND_EMAIL_INLINE)) {
            repository.initNotification(context);
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                String content = String.valueOf(remoteInput.getCharSequence(Constants.KEY_INLINE_REPLY));
                repository.sendEmail(context, content);
            } else {
                Intent activityIntent = new Intent(context, MainActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activityIntent);
            }
        } else if(intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (repository.getOnBoot(context)) {
                repository.initNotification(context);
            }
        }
    }
}
