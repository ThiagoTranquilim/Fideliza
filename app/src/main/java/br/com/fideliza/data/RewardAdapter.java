package br.com.fideliza.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fideliza.R;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private List<Reward> rewards;

    public RewardAdapter(List<Reward> rewards) {
        this.rewards = rewards;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewards.get(position);
        holder.empresaTextView.setText(reward.getEmpresa());
        holder.descricaoTextView.setText(reward.getDescricao());
        holder.dataTextView.setText(reward.getData());
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public static class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView empresaTextView, descricaoTextView, dataTextView;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            empresaTextView = itemView.findViewById(R.id.empresaTextView);
            descricaoTextView = itemView.findViewById(R.id.descricaoTextView);
            dataTextView = itemView.findViewById(R.id.dataTextView);
        }
    }
}
