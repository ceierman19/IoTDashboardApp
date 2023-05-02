package com.example.iotdashboardapp;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.iotdashboardapp.databinding.FragmentSensorBinding;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sensor}.
 */
public class MySensorRecyclerViewAdapter extends RecyclerView.Adapter<MySensorRecyclerViewAdapter.ViewHolder> {
    public interface RecyclerViewDelegate {
        void didSelect(int index);
    }
    public RecyclerViewDelegate delegate = null;
    private final List<Sensor> mValues;

    public MySensorRecyclerViewAdapter(List<Sensor> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentSensorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("Sensor ID: %s", mValues.get(position).reading_id));
        holder.mContentView.setText(String.format("Reading Type: %s", mValues.get(position).reading_type));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Sensor mItem;

        public ViewHolder(FragmentSensorBinding binding) {
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