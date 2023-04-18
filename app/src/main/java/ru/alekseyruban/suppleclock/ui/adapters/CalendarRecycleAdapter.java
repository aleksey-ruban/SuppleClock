package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.CalendarDialogItemBinding;

public class CalendarRecycleAdapter extends RecyclerView.Adapter<CalendarRecycleAdapter.CalendarAlarmItemHolder> {

    List<AlarmClockItem> data;

    public CalendarRecycleAdapter() {
        this.data = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<AlarmClockItem> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalendarRecycleAdapter.CalendarAlarmItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalendarDialogItemBinding mBinding = CalendarDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new CalendarRecycleAdapter.CalendarAlarmItemHolder(mBinding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CalendarRecycleAdapter.CalendarAlarmItemHolder holder, int position) {
        CalendarDialogItemBinding binding = holder.binding;
        binding.nameTextView.setText(data.get(position).getAlarmName());
        binding.timeTextView.setText(data.get(position).getAlarmTime());
        binding.activatedSwitch.setChecked(data.get(position).getActivated());
        binding.repeatingModeTextView.setText(data.get(position).getRepeating_mode());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CalendarAlarmItemHolder extends RecyclerView.ViewHolder {
        public CalendarDialogItemBinding binding;

        public CalendarAlarmItemHolder(@NonNull View itemView) {
            super(itemView);

            binding = CalendarDialogItemBinding.bind(itemView);
        }
    }

}
