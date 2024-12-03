package br.com.fideliza.servidor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConexaoServidor {

    // Método para executar a conexão do servidor em uma Thread
    public static void conexao(final String mensagem, final ServerCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resposta = "";
                Socket socket = null;
                BufferedReader servidorReader = null;
                BufferedWriter servidorWriter = null;

                try {
                    // Conecta ao servidor
                    socket = new Socket("54.94.21.251", 12345);

                    // Inicializa os leitores e escritores
                    servidorReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    servidorWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    // Envia a mensagem para o servidor
                    servidorWriter.write(mensagem);
                    servidorWriter.newLine();
                    servidorWriter.flush();

                    // Lê a resposta do servidor
                    resposta = servidorReader.readLine();

                } catch (Exception e) {
                    resposta = "Erro no cliente: " + e.getMessage();
                } finally {
                    try {
                        if (servidorReader != null) {
                            servidorReader.close();
                            Log.d("ConexaoServidor", "BufferedReader fechado.");
                        }
                        if (servidorWriter != null) {
                            servidorWriter.close();
                            Log.d("ConexaoServidor", "BufferedWriter fechado.");
                        }
                        if (socket != null) {
                            socket.close();
                            Log.d("ConexaoServidor", "Socket fechado.");
                        }
                    } catch (Exception e) {
                        resposta += "\nErro ao fechar recursos do cliente: " + e.getMessage();
                        Log.e("ConexaoServidor", "Erro ao fechar recursos: " + e.getMessage());
                    }
                }

                // Retorna a resposta através do callback diretamente
                if (callback != null) {
                    callback.onResult(resposta);
                }
            }
        })  .start();
    }
}