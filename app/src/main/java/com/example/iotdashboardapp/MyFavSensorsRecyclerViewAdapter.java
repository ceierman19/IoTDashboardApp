package com.example.iotdashboardapp;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.iotdashboardapp.databinding.FragmentFavSensorBinding;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FavSensor}.
 */
public class MyFavSensorsRecyclerViewAdapter extends RecyclerView.Adapter<MyFavSensorsRecyclerViewAdapter.ViewHolder> {
    public interface RecyclerViewDelegate {
        void didSelect(int index);
    }
    public MyFavSensorsRecyclerViewAdapter.RecyclerViewDelegate delegate = null;
    private final List<FavSensor> mValues;

    public MyFavSensorsRecyclerViewAdapter(List<FavSensor> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentFavSensorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(String.format("Sensor: %s", mValues.get(position).reading_type));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public FavSensor mItem;

        public ViewHolder(FragmentFavSensorBinding binding) {
            super(binding.getRoot());
            View view = binding.getRoot();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int row = getLayoutPosition();
                    delegate.didSelect(row);
                }
            });
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}