package br.com.grupo06.wishlist.domain.repository;

import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
}
