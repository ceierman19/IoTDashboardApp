package com.example.iotdashboardapp;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.iotdashboardapp.databinding.FragmentItem2Binding;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sensor}.
 */
public class MySensorRecyclerViewAdapter extends RecyclerView.Adapter<MySensorRecyclerViewAdapter.ViewHolder> {
    private final List<Sensor> mValues;

    public MySensorRecyclerViewAdapter(List<Sensor> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItem2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("%d", mValues.get(position).sensor_id));
        holder.mContentView.setText(String.format("%s", mValues.get(position).sensor_name));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Sensor mItem;

        public ViewHolder(FragmentItem2Binding binding) {
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