package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ClienteBuilder;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoBuilder;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.modelViews.ClienteProdutoSimples;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WishlistServiceTest {

    @Autowired
    WishlistService wishlistServiceTest;

    @Autowired
    ProdutoService produtoServiceTest;

    @Autowired
    ClienteService clienteServiceTest;


    @Test
    void inserirProdutoNaWishlist() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(0);
        ProdutoEntity produto = new ProdutoBuilder().defaultValues(0);
        // when
        ClienteEntity clienteFavoritado = wishlistServiceTest.inserirProdutoNaWishlist(cliente,produto);
        //then
        assertThat(clienteFavoritado.getProdutos().get(0).getCodigo()).isEqualTo(produto.getCodigo());
    }

    @Test
    void deletarProdutoNaWishlist() throws Exception {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(0);
        ProdutoEntity produto = new ProdutoBuilder().defaultValues(1);
        cliente.getProdutos().add(produto);
        // when
        wishlistServiceTest.deletarProdutoNaWishlist(cliente,produto);
        //then
        assertThat(wishlistServiceTest).doesNotHaveSameClassAs(produto); //TBC
    }

    @Test
    void buscarProdutoNaWishlistDoCliente() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(1);
        ProdutoEntity produto = new ProdutoBuilder().defaultValues(2);
        produtoServiceTest.salvar(produto);
        cliente.getProdutos().add(produto);
        clienteServiceTest.salvar(cliente);
        // when
        ClienteProdutoSimples clienteProdutoSimples =
                wishlistServiceTest.buscarProdutoNaWishlistDoCliente(cliente,produto);
        //then
        assertThat(clienteProdutoSimples.getProduto().getCodigo()).isEqualTo(produto.getCodigo());
    }
}