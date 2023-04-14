package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.ScheduledWeekItem;
import ru.alekseyruban.suppleclock.databinding.ScheduledWeekItemBinding;

public class ScheduledWeekAdapter extends RecyclerView.Adapter<ScheduledWeekAdapter.ScheduledWeekItemViewHolder> {

    List<ScheduledWeekItem> data;

    public ScheduledWeekAdapter() {
        this.data = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ScheduledWeekItem> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduledWeekAdapter.ScheduledWeekItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScheduledWeekItemBinding mBinding = ScheduledWeekItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new ScheduledWeekAdapter.ScheduledWeekItemViewHolder(mBinding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ScheduledWeekAdapter.ScheduledWeekItemViewHolder holder, int position) {
        ScheduledWeekItemBinding binding = holder.binding;
        ArrayList<Integer> wakingUpHours = data.get(position).getWakingUpHours();
        ArrayList<Integer> wakingUpMinutes = data.get(position).getWakingUpMinutes();
        ArrayList<Integer> holidays = data.get(position).getHolidays();
        binding.mondayTime.setText(wakingUpHours.get(0) + ":" + (wakingUpMinutes.get(0) == 0 ? "00" : wakingUpMinutes.get(0)));
        binding.tuesdayTime.setText(wakingUpHours.get(1) + ":" + (wakingUpMinutes.get(1) == 0 ? "00" : wakingUpMinutes.get(1)));
        binding.wednesdayTime.setText(wakingUpHours.get(2) + ":" + (wakingUpMinutes.get(2) == 0 ? "00" : wakingUpMinutes.get(2)));
        binding.thursdayTime.setText(wakingUpHours.get(3) + ":" + (wakingUpMinutes.get(3) == 0 ? "00" : wakingUpMinutes.get(3)));
        binding.fridayTime.setText(wakingUpHours.get(4) + ":" + (wakingUpMinutes.get(4) == 0 ? "00" : wakingUpMinutes.get(4)));
        binding.saturdayTime.setText(wakingUpHours.get(5) + ":" + (wakingUpMinutes.get(5) == 0 ? "00" : wakingUpMinutes.get(5)));
        binding.sundayTime.setText(wakingUpHours.get(6) + ":" + (wakingUpMinutes.get(6) == 0 ? "00" : wakingUpMinutes.get(6)));
        if (holidays.contains(0)) {
            binding.mondayTime.setAlpha(0.0F);
            binding.mondayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(1)) {
            binding.tuesdayTime.setAlpha(0.0F);
            binding.tuesdayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(2)) {
            binding.wednesdayTime.setAlpha(0.0F);
            binding.wednesdayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(3)) {
            binding.thursdayTime.setAlpha(0.0F);
            binding.thursdayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(4)) {
            binding.fridayTime.setAlpha(0.0F);
            binding.fridayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(5)) {
            binding.saturdayTime.setAlpha(0.0F);
            binding.saturdayIcon.setAlpha(1.0F);
        }
        if (holidays.contains(6)) {
            binding.sundayTime.setAlpha(0.0F);
            binding.sundayIcon.setAlpha(1.0F);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ScheduledWeekItemViewHolder extends RecyclerView.ViewHolder {
        public ScheduledWeekItemBinding binding;

        public ScheduledWeekItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ScheduledWeekItemBinding.bind(itemView);
        }
    }

}

