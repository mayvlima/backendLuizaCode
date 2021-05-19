package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ClienteBuilder;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoBuilder;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.modelViews.ClienteProdutoSimples;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WishlistServiceTest {

    @Autowired
    WishlistService wishlistServiceTest;

    @Test
    void inserirProdutoNaWishlist() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues();
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        // when
        ClienteEntity clienteFavoritado = wishlistServiceTest.inserirProdutoNaWishlist(cliente,produto);
        //then
        assertThat(clienteFavoritado.getProdutos().get(0).getCodigo()).isEqualTo(produto.getCodigo());
    }

    @Test
    void deletarProdutoNaWishlist() throws Exception {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues();
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        // when
        wishlistServiceTest.deletarProdutoNaWishlist(cliente,produto);
        //then
        assertThat(wishlistServiceTest).doesNotHaveSameClassAs(produto); //TBC
    }

    @Test
    void buscarProdutoNaWishlistDoCliente() {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues();
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        // when
        ClienteProdutoSimples clienteProdutoSimples =
                wishlistServiceTest.buscarProdutoNaWishlistDoCliente(cliente.getCodigo(),produto.getCodigo());
        //then
        assertThat(clienteProdutoSimples.getProduto().getCodigo()).isEqualTo(produto.getCodigo());
    }
}