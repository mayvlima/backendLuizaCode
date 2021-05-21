package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.entity.ClienteBuilder;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.repository.ClienteRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteController clienteController;

    // Variavel para Codigos gerados pelo banco
    private Integer code1, code2, code3;

    @BeforeAll
    public void setup(){
        ClienteEntity cliente1 = new ClienteBuilder().defaultValues(0);
        ClienteEntity cliente2 = new ClienteBuilder().defaultValues(1);
        ClienteEntity cliente3 = new ClienteBuilder().defaultValues(2);
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        clienteRepository.save(cliente3);
        code1 = cliente1.getCodigo();
        code2 = cliente2.getCodigo();
        code3 = cliente3.getCodigo();
    }

    // Testa o metodo 'listar()'
    @Test
    void listarClientesExistentes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'listarPorCodigo()'
    @Test
    void listarPorCodigoExistente() throws Exception {
        Integer code = code2;
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'listarPorCodigo()' com codigo inexistente
    @Test
    void listarPorCodigoInexistente() throws Exception {
        Integer code = 0;
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Testa o metodo 'incluir()'
    @Test
    void incluirClienteNovo() throws Exception {
        ClienteEntity clienteNovo = new ClienteBuilder().defaultValues(3);
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(clienteNovo)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'incluir()' com campo obrigatorio vazio
    @Test
    void incluirClienteComCpfVazio() throws Exception {
        ClienteEntity clienteNovo = new ClienteBuilder().defaultValues(3);
        clienteNovo.setCpf("");
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(clienteNovo)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    // Testa o metodo 'atualizar()'
    @Test
    void atualizarClienteExistente() throws Exception {
        ClienteEntity cliente1 = new ClienteBuilder().defaultValues(1);
        cliente1.setCodigo(code1);
        cliente1.setNumero("46");
        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(cliente1)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'atualizar()' com cliente inexistente
    @Test
    void atualizarClienteInexiste() throws Exception {
        ClienteEntity cliente1 = new ClienteBuilder().defaultValues(1);
        cliente1.setCodigo(0);
        cliente1.setNumero("46");
        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(cliente1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Testa o metodo 'excluir()'
    @Test
    void excluir() throws Exception {
        Integer code = code3;
        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'excluir()' usando codigo invalido
    @Test
    void excluirClientePorCodigoInvalido() throws Exception {
        Integer code = -1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Converte Object -> Json
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}