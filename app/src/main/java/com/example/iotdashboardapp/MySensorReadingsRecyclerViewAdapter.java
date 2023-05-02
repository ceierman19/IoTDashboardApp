package com.example.iotdashboardapp;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.iotdashboardapp.databinding.FragmentSensorReadingsBinding;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SensorReadings}.
 */
public class MySensorReadingsRecyclerViewAdapter extends RecyclerView.Adapter<MySensorReadingsRecyclerViewAdapter.ViewHolder> {
    private final List<SensorReadings> mValues;

    public MySensorReadingsRecyclerViewAdapter(List<SensorReadings> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentSensorReadingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("Reading Type: %s", mValues.get(position).reading_type));
        holder.mContentView.setText(String.format("Reading Value: %d", mValues.get(position).reading_val));
        holder.mContentView.setText(String.format("Reading Time: %s", mValues.get(position).reading_time));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public SensorReadings mItem;

        public ViewHolder(FragmentSensorReadingsBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}