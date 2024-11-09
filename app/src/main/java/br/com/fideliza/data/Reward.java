package br.com.fideliza.data;

public class Reward {
    private String empresa;
    private String descricao;
    private String data;

    public Reward(String empresa, String descricao, String data) {
        this.empresa = empresa;
        this.descricao = descricao;
        this.data = data;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }
}
