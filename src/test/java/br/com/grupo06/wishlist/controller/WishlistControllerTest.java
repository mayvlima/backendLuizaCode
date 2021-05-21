package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.dto.WishlistDto;
import br.com.grupo06.wishlist.domain.entity.ClienteBuilder;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoBuilder;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.repository.ClienteRepository;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
import br.com.grupo06.wishlist.service.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private WishlistController wishlistController;

    // Variavel para Codigos gerados pelo banco
    private Integer cl1, cl2;
    private Integer p1, p2;

    @BeforeAll
    public void setup(){
        ClienteEntity cliente1 = new ClienteBuilder().defaultValues(1);
        ClienteEntity cliente2 = new ClienteBuilder().defaultValues(2);

        ProdutoEntity produto1 = new ProdutoBuilder().defaultValues(1);
        ProdutoEntity produto2 = new ProdutoBuilder().defaultValues(2);

        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        cliente2.getProdutos().add(produto1);
        produto1.getClientes().add(cliente2);

        clienteRepository.save(cliente2);
        produtoRepository.save(produto1);

        cl1 = cliente1.getCodigo();
        cl2 = cliente2.getCodigo();

        p1 = produto1.getCodigo();
        p2 = produto2.getCodigo();
    }

    //Obs: O controller dá um status ok mesmo se a lista do cliente é vazia
    @Test
    void wishlistDoCliente() throws Exception {
        Integer code = cl1;
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Espero o erro Not Found quando é dado um cliente que não existe
    @Test
    void wishlistDEClienteQueNaoExiste() throws Exception {
        Integer code = 3;
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + code)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void salvarNovoProdutoWishlist() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl1,p1);
        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void salvarNovoProdutoWishlistDeClienteQueNaoExiste() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(3,p1);
        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void salvarProdutoQueNaoExisteWishlistDeCliente() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl1,3);
        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void procurarProdutoNaWishlistdoCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/buscar?id_cliente="+cl2+"&id_produto="+p1)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void procurarProdutoQueNaoEstaWishlistdoCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/buscar?id_cliente="+cl2+"&id_produto="+p2)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void procurarProdutoNaWishlistdoClienteQueNaoExiste() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/buscar?id_cliente="+3+"&id_produto="+p2)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void procurarProdutoQueNaoExisteWishlistdoCliente() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl2,3);
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/buscar?id_cliente="+cl2+"&id_produto="+3)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deletarProdutoDaWishlist() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl2,p1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(wishlistDto)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deletarProdutoQueNãoEstaNaWishlistDoCliente() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl2,p2);
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void deletarProdutoNaWishlistDeClienteQueNãoExiste() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(3,p2);
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deletarProdutoQueNaoExisteNaWishlistDoCliente() throws Exception {
        WishlistDto wishlistDto = new WishlistDto(cl1,3);
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(wishlistDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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