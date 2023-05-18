package ru.alekseyruban.suppleclock.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ru.alekseyruban.suppleclock.data.models.AchievementItem;
import ru.alekseyruban.suppleclock.databinding.AchievementItemBinding;

public class AchievementsRecycleAdapter extends RecyclerView.Adapter<AchievementsRecycleAdapter.AchievementsRecyclerItemViewHolder> {

    private List<AchievementItem> data;

    private Context context;

    public AchievementsRecycleAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<AchievementItem> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AchievementsRecycleAdapter.AchievementsRecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AchievementItemBinding mBinding = AchievementItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.getRoot().setLayoutParams(lp);

        return new AchievementsRecycleAdapter.AchievementsRecyclerItemViewHolder(mBinding.getRoot());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull AchievementsRecycleAdapter.AchievementsRecyclerItemViewHolder holder, int position) {
        String uri = data.get(position).getImageUri();
        @SuppressLint("DiscouragedApi") int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

        holder.binding.image.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), imageResource, null));

        holder.binding.name.setText(data.get(position).getName());
        holder.binding.description.setText(data.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class AchievementsRecyclerItemViewHolder extends RecyclerView.ViewHolder {
        public AchievementItemBinding binding;

        public AchievementsRecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = AchievementItemBinding.bind(itemView);
        }
    }

}
