package me.prester.remindmail.util;

import me.prester.remindmail.backend.NotificationHelper;
import me.prester.remindmail.backend.PreferencesHelper;
import me.prester.remindmail.backend.Repository;
import me.prester.remindmail.main.MainViewModelFactory;
import me.prester.remindmail.settings.SettingsViewModelFactory;
import me.prester.remindmail.share.ShareViewModelFactory;

public class InjectorUtils {
    public static Repository getRepository() {
        return Repository.getInstance();
    }

    public static PreferencesHelper getPreferencesHelper() {
        return PreferencesHelper.getInstance();
    }

    public static NotificationHelper getNotificationHelper() {
        return NotificationHelper.getInstance();
    }

    public static MainViewModelFactory provideMainViewModelFactory() {
        Repository repository = getRepository();
        return new MainViewModelFactory(repository);
    }

    public static SettingsViewModelFactory provideSettingsViewModelFactory() {
        Repository repository = getRepository();
        return new SettingsViewModelFactory(repository);
    }

    public static ShareViewModelFactory provideShareViewModelFactory() {
        Repository repository = getRepository();
        return new ShareViewModelFactory(repository);
    }
}
