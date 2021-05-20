package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.entity.ProdutoBuilder;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
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
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoController produtoController;

    private Integer code1, code2;

    @BeforeAll
     public void setup(){
        ProdutoEntity produto1 = new ProdutoBuilder().defaultValues();
        ProdutoEntity produto2 = new ProdutoBuilder().defaultValues();
        produto2.setNome("Smartphone");
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);
        code1 = produto1.getCodigo();
        code2 = produto2.getCodigo();
    }

    // Testa o metodo 'listar()'
    @Test
    void listar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'listarPorCodigo()'
    @Test
    void listarPorCodigo() throws Exception {
        Integer code = code2;
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'incluir()'
    @Test
    void incluir() throws Exception {
        ProdutoEntity produtoNovo = new ProdutoBuilder().defaultValues();
        produtoNovo.setNome("Tablet");
        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(produtoNovo)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'atualizar()'
    @Test
    void atualizar() throws Exception {
        ProdutoEntity produto1 = new ProdutoBuilder().defaultValues();
        produto1.setCodigo(code1);
        produto1.setQuantidadeEstoque(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(produto1)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testa o metodo 'excluir()'
    @Test
    void excluir() throws Exception {
        Integer code = code2;
        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Encerra limpando o banco
    @AfterAll
    public void fim(){
        produtoRepository.deleteAll();
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