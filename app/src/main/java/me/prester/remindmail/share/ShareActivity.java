package me.prester.remindmail.share;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import me.prester.remindmail.R;
import me.prester.remindmail.util.InjectorUtils;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ShareTheme);
        super.onCreate(savedInstanceState);
        ShareViewModel mShareViewModel = new ViewModelProvider(this, InjectorUtils.provideShareViewModelFactory())
                .get(ShareViewModel.class);
        mShareViewModel.sendEmail(this, getIntent().getStringExtra(Intent.EXTRA_TEXT));
        finish();
    }
}
