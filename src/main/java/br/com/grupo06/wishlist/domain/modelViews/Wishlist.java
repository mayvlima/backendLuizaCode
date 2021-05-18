package br.com.grupo06.wishlist.domain.modelViews;

import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;

import java.util.List;

public class Wishlist {

    private Integer id;

    private String nome;

    private String email;

    private List<ProdutoEntity> produtos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ProdutoEntity> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoEntity> produto) {
        this.produtos = produto;
    }
}
