package me.prester.remindmail.settings;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import me.prester.remindmail.backend.Repository;

class SettingsViewModel extends ViewModel {
    private Repository repository;

    SettingsViewModel(Repository repository) {
        this.repository = repository;
    }

    void initNotification(Context context) {
        repository.initNotification(context);
    }

}
