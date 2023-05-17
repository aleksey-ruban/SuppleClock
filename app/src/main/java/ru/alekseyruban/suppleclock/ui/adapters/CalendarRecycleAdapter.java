package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.AlarmClockItemBinding;
import ru.alekseyruban.suppleclock.databinding.CalendarDialogItemBinding;
import ru.alekseyruban.suppleclock.ui.alarms_list.OnPresentableAlarmActionsListener;

public class CalendarRecycleAdapter extends RecyclerView.Adapter<CalendarRecycleAdapter.CalendarAlarmItemHolder> {

    private List<PresentableAlarmClockItem> data;

    private final OnPresentableAlarmActionsListener listener;

    public CalendarRecycleAdapter(OnPresentableAlarmActionsListener listener) {
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
    public CalendarRecycleAdapter.CalendarAlarmItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalendarDialogItemBinding mBinding = CalendarDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new CalendarRecycleAdapter.CalendarAlarmItemHolder(mBinding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CalendarRecycleAdapter.CalendarAlarmItemHolder holder, int position) {
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

    static class CalendarAlarmItemHolder extends RecyclerView.ViewHolder {
        public CalendarDialogItemBinding binding;
        public CalendarAlarmItemHolder(@NonNull View itemView) {
            super(itemView);

            binding = CalendarDialogItemBinding.bind(itemView);
        }
    }
}
