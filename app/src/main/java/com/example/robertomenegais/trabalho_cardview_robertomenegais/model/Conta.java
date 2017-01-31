package com.example.robertomenegais.trabalho_cardview_robertomenegais.model;

        import android.net.Uri;
        import java.io.Serializable;

public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String nome;
    public String sobrenome;
    public String cpf;
    public String tipo;
    public String urlFoto;
    public Double limite;
    public Double saldo;

    @Override
    public String toString() {
        return "Conta{"
                + "id='" + id + '\''
                + ", tipo='" + tipo + '\''
                + ", nome='" + nome + '\''
                + ", sobrenome='" + sobrenome + '\''
                + ", urlFoto='" + urlFoto + '\''
                + ", cpf='" + cpf + '\''
                + ", limite='" + limite + '\''
                + ", saldo='" + saldo + '\''
                + '}';
    }
}
