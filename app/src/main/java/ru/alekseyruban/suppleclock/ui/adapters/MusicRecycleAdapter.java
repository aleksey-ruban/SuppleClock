package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.MusicItem;
import ru.alekseyruban.suppleclock.databinding.MusicItemBinding;

public class MusicRecycleAdapter extends RecyclerView.Adapter<MusicRecycleAdapter.MusicRecyclerViewItemViewHolder> {

    List<MusicItem> data;

    public MusicRecycleAdapter() {
        this.data = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<MusicItem> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MusicRecycleAdapter.MusicRecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicItemBinding mBinding = MusicItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new MusicRecycleAdapter.MusicRecyclerViewItemViewHolder(mBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MusicRecycleAdapter.MusicRecyclerViewItemViewHolder holder, int position) {
        holder.binding.number.setText(String.valueOf(position + 1));
        holder.binding.musicName.setText(data.get(position).getMusic_name());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MusicRecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public MusicItemBinding binding;

        public MusicRecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = MusicItemBinding.bind(itemView);
        }
    }

}
