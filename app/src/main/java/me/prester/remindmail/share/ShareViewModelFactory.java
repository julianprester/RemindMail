package me.prester.remindmail.share;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import me.prester.remindmail.backend.Repository;

public class ShareViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Repository repository;

    public ShareViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ShareViewModel.class)) {
            return (T) new ShareViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
