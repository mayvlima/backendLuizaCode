package br.com.grupo06.wishlist.domain.repository;

import br.com.grupo06.wishlist.domain.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    @Query(value = "select * from produto as produto inner join wishlist on produto.codigo = wishlist.produto_id "
            +"inner join cliente as cliente on cliente.codigo = wishlist.cliente_id "
            +"where cliente.codigo = :cliente_id", nativeQuery = true)
    public List<ProdutoEntity> buscarTodosProdutosNaWishlisDoCliente(@Param("cliente_id") Integer idCliente);

}
