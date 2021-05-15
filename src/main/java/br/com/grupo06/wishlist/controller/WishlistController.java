package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.dto.WishlistDto;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.service.ClienteService;
import br.com.grupo06.wishlist.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class WishlistController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/wishlist/{idCliente}")
    public ResponseEntity wishlistDoCliente(@PathVariable("idCliente") Integer id) {

        Optional<ClienteEntity> cliente = this.clienteService.listarPorCodigo(id);

        if (cliente.isPresent()) {
            List<ProdutoEntity> wishlist = produtoService.listarTodosProdutosNaWishlistDoCliente(id);

            return new ResponseEntity(wishlist, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }

    }

    @PostMapping("/wishlist")
    public  ResponseEntity salvarNovoProdutoWishlist(@RequestBody WishlistDto wishlistDto) {
        try {
            this.clienteService.inserirProdutoNaWishlist(wishlistDto.getId_cliente(),wishlistDto.getId_produto());
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto adicionado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/wishlist")
    public  ResponseEntity deletarProdutoDaWishlist(@RequestBody WishlistDto wishlistDto) {
        try {
            this.clienteService.deletarProdutoNaWishlist(wishlistDto.getId_cliente(),wishlistDto.getId_produto());
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/wishlist/buscar")
    public  ResponseEntity procurarProdutoNaWishlistdoCliente(@RequestBody WishlistDto wishlistDto) {
        Optional<ClienteEntity> cliente = this.clienteService.listarPorCodigo(wishlistDto.getId_cliente());
        Optional<ProdutoEntity> produto = this.produtoService.listarPorCodigo(wishlistDto.getId_produto());

        if (cliente.isPresent() && produto.isPresent()) {
            ClienteEntity resposta = this.clienteService.buscarProdutoNaWishlistDoCliente(cliente.get().getCodigo(), produto.get().getCodigo());
                if(resposta != null){
                    return new ResponseEntity(resposta, HttpStatus.FOUND);
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado na wishlist do cliente!");
                }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente ou produto não encontrado!");
        }
    }
}
