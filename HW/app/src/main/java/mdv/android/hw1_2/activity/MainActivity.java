package mdv.android.hw1_2.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import mdv.android.hw1_2.R;
import mdv.android.hw1_2.callBackInterfaces.IAddCityCallback;
import mdv.android.hw1_2.fragments.CitiesScreenFragment;

public class MainActivity extends BaseActivity {
    IAddCityCallback addCityCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setFloatingButton();
    }

    private void setFloatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            addCityCallback = (CitiesScreenFragment) getSupportFragmentManager().findFragmentById(R.id.cities);
            if (Objects.requireNonNull(addCityCallback).addCityToList()) {
                Snackbar.make(view, "Город добавлен", Snackbar.LENGTH_LONG)
                        .setAction("Отмена", view1 -> addCityCallback.deleteCityFromList()).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.dark_theme).setChecked(isDarkTheme());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_main_settings) {
            return true;
        }

        if (id == R.id.dark_theme) {
            if (item.isChecked()) {
                item.setChecked(false);
            }
            else item.setChecked(true);
            setDarkTheme(item.isChecked());
            recreate();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
