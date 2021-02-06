package mdv.android.hw1_2.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import mdv.android.hw1_2.CityCard;
import mdv.android.hw1_2.CityPreference;
import mdv.android.hw1_2.R;
import mdv.android.hw1_2.adapters.RecyclerViewAdapter;
import mdv.android.hw1_2.activity.TempScreenActivity;
import mdv.android.hw1_2.callBackInterfaces.IAdapterCallbacks;
import mdv.android.hw1_2.callBackInterfaces.IAddCityCallback;

public class CitiesScreenFragment extends Fragment implements IAddCityCallback, IAdapterCallbacks {

    private TextInputEditText enterCityEditText;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private boolean isTempScreenExists;
    private String cityName;
    private ArrayList<CityCard> list;
    private CityPreference cityPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTempScreenExists = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        cityPreference = new CityPreference(Objects.requireNonNull(getActivity()));
        cityName = cityPreference.getCity();
//        if (savedInstanceState != null) {
//            cityName = savedInstanceState.getString("city", "Moscow");
//        }
        if (isTempScreenExists) {
            showTempScreen(cityName);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("city", cityName);
        outState.putParcelableArrayList("list", list);
//        cityPreference.setList(list);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        enterCityEditText = view.findViewById(R.id.add_city_to_recycler);
    }

    private void initList(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            CityCard[] data = new CityCard[]{
                    new CityCard(getString(R.string.orsha)),
                    new CityCard(getString(R.string.vitebsk)),
                    new CityCard(getString(R.string.minsk)),
                    new CityCard(getString(R.string.moscow)),
                    new CityCard(getString(R.string.spb)),
                    new CityCard(getString(R.string.kazan)),
                    new CityCard(getString(R.string.ptz))
            };
            list = new ArrayList<>(data.length);
            list.addAll(Arrays.asList(data));
        } else {
            list = savedInstanceState.getParcelableArrayList("list");
            //list = cityPreference.getList();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerViewAdapter(list, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void showTempScreen(String city) {
        if (isTempScreenExists) {
            TempScreenFragment detail = (TempScreenFragment) Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.temp_screen);
            if (detail == null || !detail.getCityName().equals(city)) {
                detail = TempScreenFragment.newInstance(city);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.temp_screen, detail);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack("Some_key");
                fragmentTransaction.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), TempScreenActivity.class);
            intent.putExtra("index", city);
            startActivity(intent);
        }
    }


    @Override
    public void startTempScreenFragment(String city) {
        // cityName = city;
        cityPreference.setCity(city);
        showTempScreen(city);
    }

    @Override
    public boolean addCityToList() {
        String city = Objects.requireNonNull(enterCityEditText.getText()).toString();
        if (!city.isEmpty() && !adapter.checkIsItemInData(city)) {
            CityCard cityCard = new CityCard(city);
            adapter.addItem(cityCard);
            recyclerView.scrollToPosition(0);
            return true;
        } return false;
    }

    @Override
    public void deleteCityFromList() {
        adapter.removeItem();
    }


}
