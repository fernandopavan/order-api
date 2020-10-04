package com.projeto.order.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

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

        Pedido pedido = Pedido.Builder.create()
                .descricao("Pedido 1")
                .fechado(Boolean.FALSE)
                .dataHora(LocalDateTime.of(2020, 1, 1, 1, 1))
                .desconto(BigDecimal.ONE)
                .pedidoProdutos(Collections.singletonList(pedidoProduto))
                .valorTotal(BigDecimal.TEN)
                .build();

        Assertions.assertEquals("Pedido 1", pedido.getDescricao());
        Assertions.assertFalse(pedido.getFechado());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), pedido.getDataHora());
        Assertions.assertEquals(BigDecimal.ONE, pedido.getDesconto());
        Assertions.assertEquals(BigDecimal.valueOf(9.90).setScale(2), pedido.getValorTotal());
        Assertions.assertEquals(1, pedido.getPedidoProdutos().size());
    }

}