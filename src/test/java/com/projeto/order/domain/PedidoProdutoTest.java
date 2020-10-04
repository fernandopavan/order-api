package com.projeto.order.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class PedidoProdutoTest {

    @Test
    public void deveCriarAPartirDoBuilder() {
        Produto produto = Produto.Builder.create()
                .descricao("Produto 1")
                .preco(BigDecimal.TEN)
                .build();

        Pedido pedido = Pedido.Builder.create()
                .descricao("Pedido 1")
                .build();

        PedidoProduto pedidoProduto = PedidoProduto.Builder.create()
                .quantidade(1)
                .produto(produto)
                .pedido(pedido)
                .build();

        Assertions.assertEquals(1, pedidoProduto.getQuantidade());
        Assertions.assertEquals("Produto 1", pedidoProduto.getProduto().getDescricao());
        Assertions.assertEquals("Pedido 1", pedidoProduto.getPedido().getDescricao());
    }

}