package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Api("MÉTODOS PARA GERENCIAMENTO DE PRODUTOS")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    //Método para listar todos os produtos
    @ApiOperation(value = "Listar todos os produtos")
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoEntity>> listar() {
        List<ProdutoEntity> produtos = produtoService.listarTodos();

        return new ResponseEntity<List<ProdutoEntity>>(produtos, HttpStatus.OK);
    }

    //Método para listar produto por codigo
    @ApiOperation(value = "Listar um produto informando o código")
    @GetMapping("/produtos/{codigo}")
    public ResponseEntity listarPorCodigo(@PathVariable("codigo") Integer codigo) {

        Optional<ProdutoEntity> produto = this.produtoService.listarPorCodigo(codigo);

        if (produto.isPresent()) {
            return new ResponseEntity(produto, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    //Método para incluir produto
    @ApiOperation(value = "Salvar produto")
    @PostMapping("/produtos")
    public ResponseEntity incluir(@RequestBody ProdutoEntity produto) {
        try {
            this.produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.OK).body("Produto incluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Método para atualizar produto
    @ApiOperation(value = "Alterar um produto")
    @PutMapping("/produtos")
    public ResponseEntity atualizar( @RequestBody ProdutoEntity produto) {
        try {

            this.produtoService.salvar(produto);
            return ResponseEntity.status(HttpStatus.OK).body("Produto atualizado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Método para excluir um produto
    @ApiOperation(value = "Excluir um produto informando o código")
    @DeleteMapping("/produtos/{codigo}")
    public ResponseEntity excluir(@PathVariable("codigo") Integer codigo) {
        try {
            this.produtoService.excluir(codigo);
            return ResponseEntity.status(HttpStatus.OK).body("Produto excluído com sucesso!");

            //Exceção quando não encontra o id informado
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
        //Demais exceções

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

}
