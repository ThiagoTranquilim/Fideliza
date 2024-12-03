package br.com.fideliza.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        // Configura os textos principais
        holder.empresaTextView.setText(reward.getEmpresa());
        holder.descricaoTextView.setText(reward.getDescricao());

        // Formata a data recebida do servidor
        String formattedDate = formatDate(reward.getData());
        holder.dataTextView.setText(formattedDate);
    }

    // Método para formatar a data recebida
    private String formatDate(String rawDate) {
        try {
            // Define o formato de entrada (o formato que vem do servidor)
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            // Define o formato de saída (o formato desejado para exibição)
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

            // Faz o parse da data bruta e formata para o padrão desejado
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return rawDate; // Retorna a string original se houver erro
        }
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