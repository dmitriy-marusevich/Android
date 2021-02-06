package mdv.android.hw1_2.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mdv.android.hw1_2.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String NAME_SHARED_PREFERENCE = "LOGIN";
    private static final String IS_DARK_THEME = "IS_DARK_THEME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.Dark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPreferences =
                getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_DARK_THEME, true);
    }
    protected void setDarkTheme (boolean isDarkTheme) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_DARK_THEME, isDarkTheme);
        editor.apply();
    }
}
