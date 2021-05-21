package br.com.grupo06.wishlist.domain.entity;

// Classe para construcao e inicializacao de objetos Produtos
public class ProdutoBuilder {

    public ProdutoEntity defaultValues(Integer index){
        return new ProdutoEntity(
                ProdutoDefaultValues.NOME[index],
                ProdutoDefaultValues.VOLUNTARIADO[index],
                ProdutoDefaultValues.QUANTIDADEESTOQUE[index]);
    }
}
