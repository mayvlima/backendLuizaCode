package br.com.grupo06.wishlist.domain.entity;

public class ProdutoBuilder {

    public ProdutoEntity defaultValues(){
        return new ProdutoEntity(
                ProdutoDefaultValues.NOME,
                ProdutoDefaultValues.VOLUNTARIADO,
                ProdutoDefaultValues.QUANTIDADEESTOQUE);
    }
}
