package mdv.android.hw1_2.activity;

import androidx.annotation.Nullable;

import android.content.res.Configuration;
import android.os.Bundle;

import mdv.android.hw1_2.R;
import mdv.android.hw1_2.fragments.TempScreenFragment;


public class TempScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_screen);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            TempScreenFragment details = new TempScreenFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, details)
                    .commit();
        }

    }
}
