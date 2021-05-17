package br.com.grupo06.wishlist.domain.modelViews;

import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;

public class ClienteProdutoSimples {

    private String nome;

    private String cpf;

    private ProdutoEntity produto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }
}
