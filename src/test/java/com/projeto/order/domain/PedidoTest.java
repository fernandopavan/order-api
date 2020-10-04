package com.projeto.order.domain;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@SpringBootTest
class PedidoTest {

    @Test
    public void deveCriarAPartirDoBuilder() {
        Produto produto = Produto.Builder.create()
                .descricao("Produto 1")
                .inativo(Boolean.TRUE)
                .servico(Boolean.FALSE)
                .preco(BigDecimal.TEN)
                .build();

        PedidoProduto pedidoProduto = PedidoProduto.Builder.create()
                .quantidade(1)
                .produto(produto)
                .build();

        Set<PedidoProduto> pedidoProdutos = Collections.singleton(pedidoProduto);

        Pedido pedido = Pedido.Builder.create()
                .descricao("Pedido 1")
                .fechado(Boolean.FALSE)
                .dataHora(LocalDateTime.of(2020, 1, 1, 1, 1))
                .desconto(BigDecimal.ONE)
                .pedidoProdutos(pedidoProdutos)
                .valorTotal(pedidoProdutos)
                .build();

        Assertions.assertEquals("Pedido 1", pedido.getDescricao());
        Assertions.assertFalse(pedido.getFechado());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), pedido.getDataHora());
        Assertions.assertEquals(BigDecimal.ONE, pedido.getDesconto());
        Assertions.assertEquals(BigDecimal.valueOf(9.90).setScale(2), pedido.getValorTotal());
        Assertions.assertEquals(1, pedido.getPedidoProdutos().size());
    }

    @Test
    public void deveCriarComProdutosEServicos() {
        Produto produto = Produto.Builder.create()
                .descricao("Produto 1")
                .inativo(Boolean.FALSE)
                .servico(Boolean.FALSE)
                .preco(BigDecimal.TEN)
                .build();

        Produto servico = Produto.Builder.create()
                .descricao("Servico 1")
                .inativo(Boolean.FALSE)
                .servico(Boolean.TRUE)
                .preco(BigDecimal.ONE)
                .build();

        PedidoProduto pedidoProduto = PedidoProduto.Builder.create()
                .quantidade(2)
                .produto(produto)
                .build();

        PedidoProduto pedidoServico = PedidoProduto.Builder.create()
                .quantidade(1)
                .produto(servico)
                .build();

        ImmutableSet<PedidoProduto> pedidoProdutos = ImmutableSet.of(pedidoProduto, pedidoServico);

        Pedido pedido = Pedido.Builder.create()
                .descricao("Pedido 1")
                .fechado(Boolean.FALSE)
                .dataHora(LocalDateTime.of(2020, 1, 1, 1, 1))
                .desconto(BigDecimal.TEN)
                .pedidoProdutos(pedidoProdutos)
                .valorTotal(pedidoProdutos)
                .build();

        Assertions.assertEquals("Pedido 1", pedido.getDescricao());
        Assertions.assertFalse(pedido.getFechado());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), pedido.getDataHora());
        Assertions.assertEquals(BigDecimal.TEN, pedido.getDesconto());
        Assertions.assertEquals(BigDecimal.valueOf(19.0).setScale(1), pedido.getValorTotal());
        Assertions.assertEquals(2, pedido.getPedidoProdutos().size());
    }

}