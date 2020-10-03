package com.projeto.order.repositories;

import com.projeto.order.domain.PedidoProduto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoProdutoRepository extends CrudRepository<PedidoProduto, Long>, QuerydslPredicateExecutor<PedidoProduto> {

}