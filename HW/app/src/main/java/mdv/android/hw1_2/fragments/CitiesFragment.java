package mdv.android.hw1_2.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import mdv.android.hw1_2.CoatContainer;
import mdv.android.hw1_2.R;
import mdv.android.hw1_2.SettingsActivity;

public class CitiesFragment extends Fragment {
    private ListView listView;
    private TextView emptyTextView;

    private boolean isExistCoatOfArms;
    private int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isExistCoatOfArms = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }
        if (isExistCoatOfArms) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showCoatOfArms();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    public void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    private void initList() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(
                getActivity()), R.array.cities, android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showCoatOfArms();
            }
        });
    }

    private void showCoatOfArms() {
        if (isExistCoatOfArms) {
            listView.setItemChecked(currentPosition, true);
            //TODO
            SettingsFragment detail = (SettingsFragment) Objects.requireNonNull(
                    getFragmentManager()).findFragmentById(R.id.coat_of_arms);
            if (detail == null || detail.getIndex() != currentPosition) {
              //TODO
                detail = SettingsFragment.create(getCoatContainer());
            }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.coat_of_arms, detail);  // замена фрагмента
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //ft.addToBackStack(null);
            ft.addToBackStack("Some_Key");
            ft.commit();
        } else {
            // Если нельзя вывести герб рядом, откроем вторую activity
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), SettingsActivity.class);
            // и передадим туда параметры
            intent.putExtra("index", getCoatContainer());
            startActivity(intent);
        }
    }

    private CoatContainer getCoatContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);
        String[] citiesTemp = getResources().getStringArray(R.array.city_temp);
        String[] citiesOvercast = getResources().getStringArray(R.array.city_overcast);
        String[] citiesHumidity = getResources().getStringArray(R.array.city_humidity);
        CoatContainer container = new CoatContainer();
        container.position = currentPosition;
        container.cityName = cities[currentPosition];
        container.cityTemp = citiesTemp[currentPosition];
        container.overcastCity = citiesOvercast[currentPosition];
        container.humidityCity = citiesHumidity[currentPosition];
        return container;
    }

}
