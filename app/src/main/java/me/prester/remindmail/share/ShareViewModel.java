package me.prester.remindmail.share;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import me.prester.remindmail.backend.Repository;

public class ShareViewModel extends ViewModel {
    private Repository repository;

    ShareViewModel(Repository repository) {
        this.repository = repository;
    }

    public void sendEmail(Context context, String text) {
        repository.sendEmail(context, text);
    }

}
