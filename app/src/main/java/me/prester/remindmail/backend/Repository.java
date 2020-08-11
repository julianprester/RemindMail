package me.prester.remindmail.backend;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import me.prester.remindmail.util.Constants;
import me.prester.remindmail.util.InjectorUtils;
import me.prester.remindmail.workers.EmailWorker;

public class Repository {

    private static Repository instance;
    private PreferencesHelper mPreferencesHelper;
    private NotificationHelper mNotificationHelper;

    private Repository(PreferencesHelper preferencesHelper, NotificationHelper notificationHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mNotificationHelper = notificationHelper;
    }

    public void sendEmail(Context context, String text) {
        Data email = getData(context, text);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(EmailWorker.class)
                .setConstraints(constraints)
                .setInputData(email)
                .build();
        WorkManager.getInstance(context).enqueue(request);
    }

    private Data getData(Context context, String text) {
        Data.Builder data = new Data.Builder();
        data.putString(Constants.KEY_WORKER_DATA_TEXT, text);
        data.putBoolean(Constants.KEY_WORKER_DATA_SUBJECT, mPreferencesHelper.getSubject(context));
        data.putString(Constants.KEY_WORKER_DATA_SUBJECT_LINE, mPreferencesHelper.getSubjectLine(context));
        data.putString(Constants.KEY_WORKER_DATA_SERVER, mPreferencesHelper.getServer(context));
        data.putString(Constants.KEY_WORKER_DATA_SENDER, mPreferencesHelper.getSender(context));
        data.putBoolean(Constants.KEY_WORKER_DATA_AUTH, mPreferencesHelper.getAuth(context));
        data.putInt(Constants.KEY_WORKER_DATA_PORT, mPreferencesHelper.getPort(context));
        data.putString(Constants.KEY_WORKER_DATA_RECIPIENT, mPreferencesHelper.getRecipient(context));
        data.putString(Constants.KEY_WORKER_DATA_USERNAME, mPreferencesHelper.getUsername(context));
        data.putString(Constants.KEY_WORKER_DATA_PASSWORD, mPreferencesHelper.getPassword(context));
        data.putString(Constants.KEY_WORKER_DATA_SECURITY, mPreferencesHelper.getSecurity(context));
        return data.build();
    }

    boolean getOnBoot(Context context) {
        return mPreferencesHelper.getOnBoot(context);
    }

    public void initDefaultPreferenceValues(Context context) {
        mPreferencesHelper.initDefaultPreferenceValues(context);
    }

    public void initNotification(Context context) {
        mNotificationHelper.createNotificationChannel(context);
        if(mPreferencesHelper.getNotification(context)) {
            mNotificationHelper.createNotification(context);
        } else {
            mNotificationHelper.cancel(context);
        }
    }

    public static synchronized Repository getInstance() {
        if(instance == null) {
            instance = new Repository(InjectorUtils.getPreferencesHelper(), InjectorUtils.getNotificationHelper());
        }
        return instance;
    }
}
