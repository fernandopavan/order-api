package com.projeto.order.repositories;

import com.projeto.order.domain.Produto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long>, QuerydslPredicateExecutor<Produto> {

}