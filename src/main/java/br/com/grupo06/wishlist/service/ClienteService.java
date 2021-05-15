package br.com.grupo06.wishlist.service;


import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.repository.ClienteRepository;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    //Método para buscar todos os registros da tabela
    public List<ClienteEntity> listarTodos(){
        return (List<ClienteEntity>) clienteRepository.findAll();
    }

    //Método que busca um registro pelo codigo no banco de dados e retorna este registro
    public Optional<ClienteEntity> listarPorCodigo(Integer codigo){
        return clienteRepository.findById(codigo);
    }

    //Método que salva o registro no banco de dados
    public ClienteEntity salvar(ClienteEntity cliente) throws ExcecaoEsperada {
        Integer codigoCliente = cliente.getCodigo() != null ? cliente.getCodigo() : 0;
        if(cliente.getNome().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o nome do cliente!");
        } else if(cliente.getCpf().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o CPF do cliente!");
        } else if(cliente.getTelefone().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o telfone do cliente!");
        }else if(cliente.getEmail().trim().equals("")) {
            throw new ExcecaoEsperada("Por favor, informe o e-mail do cliente!");
        }else if(cliente.getLogradouro().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o endereço do cliente!");
        }else if(cliente.getNumero().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o número do endereço do cliente!");
        }else if(cliente.getBairro().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o bairro do endereço do cliente!");
        }else if(cliente.getCidade().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe a cidade do endereço do cliente!");
        }else if(cliente.getEstado().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o estado da cidade de endereço do cliente!");
        }else if(cliente.getCep().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o CEP de endereço do cliente!");
        }
        //Valida se já existe um cliente com o mesmo CPF
        ClienteEntity cli = new ClienteEntity();
        cli.setCpf(cliente.getCpf());
        Example<ClienteEntity> example = Example.of(cli);
        List<ClienteEntity> lista = clienteRepository.findAll(example);
        for (ClienteEntity c: lista) {
            if(!c.getCodigo().equals(cliente.getCodigo())){
                throw new ExcecaoEsperada("CPF já cadastrado na base de dados!!");
            }
        }
        return clienteRepository.save(cliente);

    }
    //Método que atualiza um registro no banco
    public ClienteEntity atualizar(ClienteEntity cliente){
        ClienteEntity retorno = clienteRepository.getOne(cliente.getCodigo());
        if(retorno != null){
            retorno.setNome(cliente.getNome());
            retorno.setCpf(cliente.getCpf());
            retorno.setTelefone(cliente.getTelefone());
            retorno.setEmail(cliente.getEmail());
            retorno.setLogradouro(cliente.getLogradouro());
            retorno.setNumero(cliente.getNumero());
            retorno.setComplemento(cliente.getComplemento());
            retorno.setBairro(cliente.getBairro());
            retorno.setCidade(cliente.getCidade());
            retorno.setEstado(cliente.getEstado());
            retorno.setCep(cliente.getCep());

            clienteRepository.save(retorno);
        }
        return clienteRepository.save(cliente);
    }
    //Método para excluir um registro
    public void excluir (Integer codigo){
        clienteRepository.deleteById(codigo);
    }

    //Método para retornar todos os produtos da wishlist de um determinado cliente
    public List<ProdutoEntity> buscarWishlist(Integer id){
        ClienteEntity retorno = clienteRepository.getOne(id);
        return retorno.getProdutos();
    }

    //Método para add produto na wishlist do cliente
    public Object inserirProdutoNaWishlist(Integer id_cliente, Integer id_produto) throws Exception {
        ClienteEntity cliente = clienteRepository.getOne(id_cliente);
        ProdutoEntity produto = produtoRepository.getOne(id_produto);

        if (cliente != null && produto != null) {
            if(!cliente.getProdutos().contains(produto)){

            cliente.getProdutos().add(produto);
            return clienteRepository.save(cliente);}else{
                throw new ExcecaoEsperada("Produto já adicionado na wishlist do cliente!");
            }
        }
        throw new ExcecaoEsperada("Cliente ou Produto inválido!");
    }
}
