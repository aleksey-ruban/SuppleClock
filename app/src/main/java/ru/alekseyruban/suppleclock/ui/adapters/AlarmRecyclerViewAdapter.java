package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.AlarmClockItemBinding;
import ru.alekseyruban.suppleclock.ui.alarms_list.OnPresentableAlarmActionsListener;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmRecyclerViewItemViewHolder> {

    private List<PresentableAlarmClockItem> data;
    private final OnPresentableAlarmActionsListener listener;

    public AlarmRecyclerViewAdapter(OnPresentableAlarmActionsListener listener) {
        this.data = new ArrayList<>();
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<PresentableAlarmClockItem> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmRecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlarmClockItemBinding mBinding = AlarmClockItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new AlarmRecyclerViewItemViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmRecyclerViewItemViewHolder holder, int position) {
        holder.binding.nameTextView.setText(data.get(position).getAlarmName());
        holder.binding.timeTextView.setText(data.get(position).getAlarmTime());
        holder.binding.activatedSwitch.setChecked(data.get(position).getActivated());
        holder.binding.repeatingModeTextView.setText(data.get(position).getRepeating_mode());

        holder.binding.activatedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onActivatedChanged(data.get(position)));
        holder.binding.moreDetailsButton.setOnClickListener(v -> listener.onMoreDetailsClick(data.get(position)));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class AlarmRecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public AlarmClockItemBinding binding;

        public AlarmRecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = AlarmClockItemBinding.bind(itemView);
        }
    }
}
