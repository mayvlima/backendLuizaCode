package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.modelViews.ClienteProdutoSimples;
import br.com.grupo06.wishlist.domain.repository.ClienteRepository;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    //Método para retornar todos os produtos da wishlist de um determinado cliente
    public List<ProdutoEntity> buscarWishlistDoCliente(Integer id){
        List<ProdutoEntity> retorno = this.produtoRepository.buscarTodosProdutosNaWishlisDoCliente(id);
        return retorno;
    }

    //Método para add produto na wishlist do cliente
    public ClienteEntity inserirProdutoNaWishlist(ClienteEntity cliente, ProdutoEntity produto) throws ExcecaoEsperada {

            List<ProdutoEntity> listaProdutos = cliente.getProdutos();

            if(listaProdutos.contains(produto)){
                throw new ExcecaoEsperada("Produto já está na wishlist do cliente!");
            }else if(listaProdutos.size() == 20){
                throw new ExcecaoEsperada("Wishlist do cliente atingiu o limite máximo de 20 produtos!");
            }else{
                cliente.getProdutos().add(produto);
                return clienteRepository.save(cliente);
            }

    }

    //Método para deletar produto na wishlist do cliente
    public Object deletarProdutoNaWishlist(ClienteEntity cliente, ProdutoEntity produto) throws Exception {

            if(cliente.getProdutos().contains(produto)){
                cliente.getProdutos().remove(produto);
                return clienteRepository.save(cliente);}
            else{
                throw new ExcecaoEsperada("Produto não está na wishlist do cliente!");
            }

    }

    public ClienteProdutoSimples buscarProdutoNaWishlistDoCliente(Integer cliente_id, Integer produto_id)  {
        ClienteEntity retorno = clienteRepository.buscarProdutoNaWishlistDoCliente(cliente_id, produto_id);

        if(retorno != null){
            ClienteProdutoSimples clienteDesejado = new ClienteProdutoSimples();

            clienteDesejado.setNome(retorno.getNome());
            clienteDesejado.setCpf(retorno.getCpf());

           for(ProdutoEntity p : retorno.getProdutos()){
                if(p.getCodigo().equals(produto_id)){
                    clienteDesejado.setProduto(p);
                    break;
                }
            }
            return clienteDesejado;
        }
        return null;
    }
}
