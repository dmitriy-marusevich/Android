package mdv.android.hw1_2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import mdv.android.hw1_2.CityCard;
import mdv.android.hw1_2.R;
import mdv.android.hw1_2.callBackInterfaces.IAdapterCallbacks;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<CityCard> data = new ArrayList<>();
    private Context context;
    private IAdapterCallbacks adapterCallbacks;

    public RecyclerViewAdapter(ArrayList<CityCard> data, IAdapterCallbacks adapterCallbacks) {
        if (data != null) {
            this.data = data;
        }
        this.adapterCallbacks = adapterCallbacks;
    }

    public void addItem(CityCard cityCard) {
        data.add(0, cityCard);
        notifyItemInserted(0);
    }

    public boolean checkIsItemInData(String city) {
        CityCard cityCard = new CityCard(city);
        return data.contains(cityCard);
    }

    public void removeItem() {
        if (!data.isEmpty()) {
            data.remove(0);
            notifyItemRemoved(0);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list_cities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.imageButton.setOnClickListener(view -> Snackbar.make(view, "Удалить город?", Snackbar.LENGTH_LONG)
                .setAction("Да", view12 -> {
                    if (!data.isEmpty()) {
                        String city = holder.textView.getText().toString();
                        CityCard cityCard = new CityCard(city);
                        int pos = data.indexOf(cityCard);
                        data.remove(cityCard);
                        notifyItemRemoved(pos);
                    }
                }).show());
        holder.textView.setText(data.get(position).getText());
        holder.itemView.setOnClickListener(view -> adapterCallbacks.startTempScreenFragment(holder.textView.getText().toString()));
        holder.itemView.setOnLongClickListener(view -> {
            Snackbar.make(view, "Удалить город?", Snackbar.LENGTH_LONG)
                    .setAction("Да", view1 -> {
                        if (!data.isEmpty()) {
                            String city = holder.textView.getText().toString();
                            CityCard cityCard = new CityCard(city);
                            int pos = data.indexOf(cityCard);
                            data.remove(cityCard);
                            notifyItemRemoved(pos);
                        }
                    }).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;

        ViewHolder(View view) {
            super(view);
            textView = itemView.findViewById(R.id.cityTextViewOnCard);
            imageButton = itemView.findViewById(R.id.delete_city_btn);
        }

    }
}
