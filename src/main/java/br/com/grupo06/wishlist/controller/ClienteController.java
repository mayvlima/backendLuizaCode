package br.com.grupo06.wishlist.controller;

import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@Api("MÉTODOS PARA GERENCIAMENTO DE CLIENTES")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Método para listar todos os clientes
    @ApiOperation(value = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de Clientes"),
            @ApiResponse(code = 500, message = "Erro"),
    })
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteEntity>> listar() {
        List<ClienteEntity> clientes = clienteService.listarTodos();

        return new ResponseEntity<List<ClienteEntity>>(clientes, HttpStatus.OK);
    }

    //Método para listar cliente por codigo
    @ApiOperation(value = "Listar um cliente informando o código")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Retorna o cliente", response = ClienteEntity.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
    })
    @GetMapping("/clientes/{codigo}")
    public ResponseEntity listarPorCodigo(@PathVariable("codigo") Integer codigo) {

        Optional<ClienteEntity> cliente = this.clienteService.listarPorCodigo(codigo);

        if (cliente.isPresent()) {
            return new ResponseEntity(cliente, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    //Método para incluir cliente
    @ApiOperation(value = "Salvar cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 201, message = "Cliente incluído com sucesso"),
            @ApiResponse(code = 500, message = "Não passou nas validações"),
    })
    @PostMapping("/clientes")
    public ResponseEntity incluir(@RequestBody ClienteEntity cliente) {
        try {
            this.clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente incluído com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Método para atualizar cliente
    @ApiOperation(value = "Alterar um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 500, message = "Não passou nas validações"),
    })
    @PutMapping("/clientes")
    public ResponseEntity atualizar( @RequestBody ClienteEntity cliente) {
        try {
            this.clienteService.atualizar(cliente);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente atualizado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
    }

    //Método para excluir um cliente
    @ApiOperation(value = "Excluir um cliente informando o código")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
            @ApiResponse(code = 500, message = "Erro"),
    })
    @DeleteMapping("/clientes/{codigo}")
    public ResponseEntity excluir(@PathVariable("codigo") Integer codigo) {
        try {
            this.clienteService.excluir(codigo);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente excluído com sucesso!");

            //Exceção quando não encontra o id informado
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
        //Demais exceções

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }

}