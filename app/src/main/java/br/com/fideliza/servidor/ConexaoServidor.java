package br.com.fideliza.servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConexaoServidor {

    public static String conexao(String mensagem) {
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
                if (servidorReader != null) servidorReader.close();
                if (servidorWriter != null) servidorWriter.close();
                if (socket != null) socket.close();
            } catch (Exception e) {
                resposta += "\nErro ao fechar recursos do cliente: " + e.getMessage();
            }
        }
        return resposta;
    }

}
