package br.com.fideliza.servidor;

import android.util.Log;

public class Gerenciador extends  Thread {

    String mensagem;

    public Gerenciador(String mensagem) {
        this.mensagem = mensagem;
        start();
    }

    @Override
    public void run() {
        String resposta = ConexaoServidor.conexao(this.mensagem);
        Log.i("Teste", resposta);
    }

}
