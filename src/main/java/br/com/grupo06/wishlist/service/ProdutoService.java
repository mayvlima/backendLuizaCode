package br.com.grupo06.wishlist.service;

import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import br.com.grupo06.wishlist.domain.excecao.ExcecaoEsperada;
import br.com.grupo06.wishlist.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    //Método para buscar todos os registros da tabela
    public List<ProdutoEntity> listarTodos(){
        return (List<ProdutoEntity>) produtoRepository.findAll();
    }

    //Método que busca um registro pelo codigo no banco de dados e retorna este registro
    public Optional<ProdutoEntity> listarPorCodigo(Integer codigo){
        return produtoRepository.findById(codigo);
    }

    //Método que salva o registro no banco de dados
    public ProdutoEntity salvar(ProdutoEntity produto) throws ExcecaoEsperada {
        Integer codigoProduto = produto.getCodigo() != null ? produto.getCodigo() : 0;
        if(produto.getNome().trim().equals("")){
            throw new ExcecaoEsperada("Por favor, informe o nome do produto!");
        } else if(produto.getValorUnitario().equals(0.00)){
            throw new ExcecaoEsperada("Por favor, informe o valor unitário do produto!");
        } else if(produto.getQuantidadeEstoque().equals(0)){
            throw new ExcecaoEsperada("Por favor, informe a quantidade de estoque do produto!");
        }
        //Valida se já existe um produto com o mesmo nome
        ProdutoEntity prod2 = new ProdutoEntity();
        prod2.setNome(produto.getNome());
        Example<ProdutoEntity> example = Example.of(prod2);
        List<ProdutoEntity> lista = produtoRepository.findAll(example);
        for (ProdutoEntity p: lista) {
            if(!p.getCodigo().equals(produto.getCodigo())){
                throw new ExcecaoEsperada("Nome do produto já existe!!");
            }
        }
         return produtoRepository.save(produto);

    }
    //Método que atualiza um registro no banco
    public ProdutoEntity atualizar(ProdutoEntity produto){
        ProdutoEntity retorno = produtoRepository.getOne(produto.getCodigo());
        if(retorno != null){
            retorno.setNome(produto.getNome());
            retorno.setValorUnitario(produto.getValorUnitario());
            retorno.setQuantidadeEstoque(produto.getQuantidadeEstoque());
            produtoRepository.save(retorno);
        }
        return produtoRepository.save(produto);
    }
    //Método para excluir um registro
    public void excluir (Integer codigo){
        produtoRepository.deleteById(codigo);
    }

    public List<ProdutoEntity> listarTodosProdutosNaWishlistDoCliente(Integer id){
        List<ProdutoEntity> retorno = this.produtoRepository.buscarTodosProdutoNaWishlisDoCliente(id);
        return retorno;
    }
}
