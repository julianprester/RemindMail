package me.prester.remindmail.backend;

import android.content.Context;

import androidx.preference.PreferenceManager;

import me.prester.remindmail.R;

public class PreferencesHelper {

    private static PreferencesHelper instance;

    private PreferencesHelper() {}

    private String getResString(Context context, int resId) {
        return context.getString(resId);
    }

    private boolean getResBool(Context context, int resId) {
        return context.getResources().getBoolean(resId);
    }

    private boolean getBoolean(Context context, int keyId, int defaultId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(getResString(context, keyId), getResBool(context, defaultId));
    }

    private String getString(Context context, int keyId, int defaultId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(getResString(context, keyId), getResString(context, defaultId));
    }

    private int getIntegerById(Context context, int keyId, int defaultId) {
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString(getResString(context, keyId), getResString(context, defaultId)));
    }

    public String getRecipient(Context context) {
        return getString(context, R.string.settings_key_recipient, R.string.settings_default_recipient);
    }

    public String getSender(Context context) {
        return getString(context, R.string.settings_key_sender, R.string.settings_default_sender);
    }

    public boolean getSubject(Context context) {
        return getBoolean(context, R.string.settings_key_subject, R.bool.settings_default_subject);
    }

    public String getSubjectLine(Context context) {
        return getString(context, R.string.settings_key_subject_line, R.string.settings_default_subject_line);
    }

    public boolean getNotification(Context context) {
        return getBoolean(context, R.string.settings_key_notification, R.bool.settings_default_notification);
    }

    public boolean getOnBoot(Context context) {
        return getBoolean(context, R.string.settings_key_on_boot, R.bool.settings_default_on_boot);
    }

    public String getServer(Context context) {
        return getString(context, R.string.settings_key_server, R.string.settings_default_server);
    }

    public int getPort(Context context) {
        return getIntegerById(context, R.string.settings_key_port, R.string.settings_default_port);
    }

    public String getSecurity(Context context) {
        return getString(context, R.string.settings_key_security, R.string.settings_default_security);
    }

    public boolean getAuth(Context context) {
        return getBoolean(context, R.string.settings_key_auth, R.bool.settings_default_auth);
    }

    public String getUsername(Context context) {
        return getString(context, R.string.settings_key_username, R.string.settings_default_username);
    }

    public String getPassword(Context context) {
        return getString(context, R.string.settings_key_password, R.string.settings_default_password);
    }

    public void initDefaultPreferenceValues(Context context) {
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
    }

    public static synchronized PreferencesHelper getInstance() {
        if(instance == null) {
            instance = new PreferencesHelper();
        }
        return instance;
    }
}
