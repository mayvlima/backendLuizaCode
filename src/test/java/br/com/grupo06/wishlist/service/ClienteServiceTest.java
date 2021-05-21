package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ClienteBuilder;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ClienteServiceTest {

    @Autowired
    ClienteService clienteServiceTest;

    @Test
    void listarTodos() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente1 = new ClienteBuilder().defaultValues(0);
        clienteServiceTest.salvar(cliente1);
        ClienteEntity cliente2 = new ClienteBuilder().defaultValues(1);
        cliente2.setCpf("95582540039");
        clienteServiceTest.salvar(cliente2);
        //when
        List<ClienteEntity> clientesEncontrados  = clienteServiceTest.listarTodos();
        //then
        assertThat(clientesEncontrados.size()).isEqualTo(2);
    }

    @Test
    void listarPorCodigo() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(2);
        clienteServiceTest.salvar(cliente);
        //when
        Optional<ClienteEntity> clienteEncontrado  = clienteServiceTest.listarPorCodigo(cliente.getCodigo());
        // then
        assertThat(clienteEncontrado.get().getCodigo()).isEqualTo(cliente.getCodigo());
    }

    @Test
    void salvar() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(3);
        cliente.setCpf("9007854357");
        //when
        ClienteEntity clienteSalvo  = clienteServiceTest.salvar(cliente);
        // then
        assertThat(clienteSalvo).isNotNull();
    }

    @Test
    void atualizar() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(0);
        clienteServiceTest.salvar(cliente);
        cliente.setCidade("Recife");
        cliente.setEstado("PE");
        //when
        ClienteEntity clienteAlterado  = clienteServiceTest.atualizar(cliente);
        // then
        assertThat(clienteAlterado.getCidade()).isEqualTo(cliente.getCidade());
    }

    @Test
    void excluir() throws ExcecaoEsperada {
        //given
        ClienteEntity cliente = new ClienteBuilder().defaultValues(4);
        clienteServiceTest.salvar(cliente);
        //when
        clienteServiceTest.excluir(cliente.getCodigo());
        // then
        assertThat(clienteServiceTest).doesNotHaveSameClassAs(cliente);
    }
}