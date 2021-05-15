package br.com.grupo06.wishlist.domain.repository;

import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    @Query(value = "select * from cliente as cliente inner join wishlist on cliente.codigo = wishlist.cliente_id "
            +"inner join produto as produto on produto.codigo = wishlist.produto_id "
            +"where cliente.codigo = :cliente_id and produto.codigo = :produto_id", nativeQuery = true)
    public ClienteEntity buscarProdutoNaWishlistDoCliente(@Param("cliente_id") Integer idCliente, @Param("produto_id") Integer idProduto);
}
