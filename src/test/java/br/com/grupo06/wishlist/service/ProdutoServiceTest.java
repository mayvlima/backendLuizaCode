package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ProdutoBuilder;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoServiceTest;

    @Test
    void listarTodos() throws ExcecaoEsperada {
        //given
        ProdutoEntity produto1 = new ProdutoBuilder().defaultValues();
        produtoServiceTest.salvar(produto1);
        ProdutoEntity produto2 = new ProdutoBuilder().defaultValues();
        produto2.setNome("Telefone");
        produtoServiceTest.salvar(produto2);
        //when
        List<ProdutoEntity> produtosEncontrados  = produtoServiceTest.listarTodos();
        //then
        assertThat(produtosEncontrados.size()).isEqualTo(2);
    }

    @Test
    void listarPorCodigo() throws ExcecaoEsperada {
        //given
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        produtoServiceTest.salvar(produto);
        //when
        Optional<ProdutoEntity> produtoEncontrado  = produtoServiceTest.listarPorCodigo(produto.getCodigo());
        // then
        assertThat(produtoEncontrado.get().getCodigo()).isEqualTo(produto.getCodigo());
    }

    @Test
    void salvar() throws ExcecaoEsperada {
        //given
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        //when
        ProdutoEntity produtoSalvo  = produtoServiceTest.salvar(produto);
        // then
        assertThat(produtoSalvo).isNotNull();
    }

    @Test
    void atualizar() throws ExcecaoEsperada {
        //given
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        produtoServiceTest.salvar(produto);
        produto.setQuantidadeEstoque(5);
        //when
        ProdutoEntity produtoAlterado  = produtoServiceTest.atualizar(produto);
        // then
        assertThat(produtoAlterado.getQuantidadeEstoque()).isEqualTo(produto.getQuantidadeEstoque());
    }

    @Test
    void excluir() throws ExcecaoEsperada {
        //given
        ProdutoEntity produto = new ProdutoBuilder().defaultValues();
        produtoServiceTest.salvar(produto);
        //when
        produtoServiceTest.excluir(produto.getCodigo());
        // then
        assertThat(produtoServiceTest).doesNotHaveSameClassAs(produto);
    }

}