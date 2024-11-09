package br.com.fideliza;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fideliza.data.Reward;
import br.com.fideliza.data.RewardAdapter;
import br.com.fideliza.servidor.ConexaoServidor;
import br.com.fideliza.servidor.ServerCallback;

public class RewardsFragment extends Fragment implements ServerCallback {

    private RecyclerView recyclerView;
    private TextView emptyMessageTextView;
    private RewardAdapter rewardAdapter;
    private List<Reward> rewards;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyMessageTextView = view.findViewById(R.id.emptyMessageTextView);

        // Inicializando o FirebaseAuth e obtendo o UID
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getUid() : null;

        // Verifica se o usuário está autenticado
        if (userId != null) {
            // Conectando ao servidor com o UID do Firebase
            ConexaoServidor.conexao("5;" + userId, this);
        } else {
            emptyMessageTextView.setText("Usuário não autenticado");
            emptyMessageTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rewards = new ArrayList<>();
        rewardAdapter = new RewardAdapter(rewards);
        recyclerView.setAdapter(rewardAdapter);

        return view;
    }

    private void parseResponse(String serverResponse) {
        rewards.clear();

        if (serverResponse.equals("Nenhum documento encontrado")) {
            // Exibir mensagem na tela se não houver recompensas
            emptyMessageTextView.setText("Nenhuma recompensa encontrada");
            emptyMessageTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyMessageTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            // Dividir a resposta e analisar cada `Document` BSON
            String[] documentStrings = serverResponse.split(";");
            for (String docString : documentStrings) {
                Document doc = Document.parse(docString);
                String empresa = doc.getString("empresa");
                String descricao = doc.getString("descricao");

                // Obter a data diretamente como um objeto Date
                Date dataObj = doc.getDate("data");
                String data = dataObj != null ? dataObj.toString() : "Data indisponível";

                rewards.add(new Reward(empresa, descricao, data));
            }

            // Atualizar o adapter para exibir os novos dados
            rewardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResult(String resultado) {

        Log.d("teste", resultado);
        // Garantir que parseResponse seja executado na thread principal
        requireActivity().runOnUiThread(() -> parseResponse(resultado));
    }
}