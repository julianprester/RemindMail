package me.prester.remindmail.main;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import me.prester.remindmail.backend.Repository;

public class MainViewModel extends ViewModel {
    private Repository repository;

    MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public void sendEmail(Context context, String text) {
        repository.sendEmail(context, text);
    }

    void initDefaultPreferenceValues(Context context) {
        repository.initDefaultPreferenceValues(context);
    }

    void initNotification(Context context) {
        repository.initNotification(context);
    }
}
