package com.projeto.order.repositories;

import com.projeto.order.domain.Pedido;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long>, QuerydslPredicateExecutor<Pedido> {

}