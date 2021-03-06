package com.projeto.order.repositories;

import com.projeto.order.domain.PedidoProduto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoProdutoRepository extends CrudRepository<PedidoProduto, UUID>, QuerydslPredicateExecutor<PedidoProduto> {

}