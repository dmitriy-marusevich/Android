package mdv.android.hw1_2.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Objects;
import mdv.android.hw1_2.CoatContainer;
import mdv.android.hw1_2.R;

public class SettingsFragment extends Fragment {
    private TextView nameCityTextView;
    private TextView tempCityTextView;
    private ImageView imageView;
    private CheckBox overcastCheckBox;
    private CheckBox humidityCheckBox;
    private TextView overcastTextView;
    private TextView humidityTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

    }
    private void initViews(View view){
        nameCityTextView = view.findViewById(R.id.nameCityTextView);
        tempCityTextView = view.findViewById(R.id.tempCityTextView);
        imageView = view.findViewById(R.id.imageView);
        overcastCheckBox = view.findViewById(R.id.overcastCheckBox);
//        overcastCheckBox.setChecked(false);
        humidityCheckBox = view.findViewById(R.id.humidityCheckBox);
//        humidityCheckBox.setChecked(false);
        overcastTextView = view.findViewById(R.id.overcastTextView);
        humidityTextView = view.findViewById(R.id.humidityTextView);

        nameCityTextView.setText(getCityName());
        tempCityTextView.setText(getCityTemp());

        if (isCheckBoxSetOvercastTextView()) {
            overcastTextView.setText(getOvercast());
        }
        else overcastTextView.setText("");
        if (isCheckBoxSetHumidityTextView()) {
            humidityTextView.setText(getHumidity());
        } else humidityTextView.setText("");

        TypedArray images = getResources().obtainTypedArray(R.array.coatofarms_imgs);
        imageView.setImageResource(images.getResourceId(getIndex(), -1));


    }

    static SettingsFragment create(CoatContainer container) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putSerializable("index", container);
        fragment.setArguments(args);
        return fragment;
    }

    int getIndex() {
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return coatContainer.position;
        } catch (Exception e) {
            return 0;
        }
    }

    String getCityName() {
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return coatContainer.cityName;
        } catch (Exception e) {
            return "";
        }
    }

    String getCityTemp(){
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments()).
                getSerializable("index"));
        try {
            return coatContainer.cityTemp;
        } catch (Exception e){
            return "";
        }
    }
    String getOvercast(){
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments()).
                getSerializable("index"));
        try {
            return coatContainer.overcastCity;
        } catch (Exception e){
            return "";
        }
    }
    String getHumidity(){
        CoatContainer coatContainer = (CoatContainer) (Objects.requireNonNull(getArguments()).
                getSerializable("index"));
        try {
            return coatContainer.humidityCity;
        } catch (Exception e){
            return "";
        }
    }

    private boolean isCheckBoxSetOvercastTextView(){
        if(overcastCheckBox.isChecked()) return true;
        else return false;
    }
    private boolean isCheckBoxSetHumidityTextView(){
        if(humidityCheckBox.isChecked()) return true;
        else return false;
    }

}
