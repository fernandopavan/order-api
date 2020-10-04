package com.projeto.order.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProdutoTest {

    @Test
    public void deveCriarAPartirDoBuilder() {
        Produto produto = Produto.Builder.create()
                .descricao("Produto 1")
                .inativo(Boolean.TRUE)
                .servico(Boolean.FALSE)
                .preco(BigDecimal.TEN)
                .build();

        Assertions.assertEquals("Produto 1", produto.getDescricao());
        Assertions.assertTrue(produto.getInativo());
        Assertions.assertFalse(produto.getServico());
        Assertions.assertEquals(BigDecimal.TEN, produto.getPreco());
    }

    @Test
    public void deveAtualizarAPartirDoBuilder() {
        Produto produto = Produto.Builder.create()
                .descricao("Produto 1")
                .inativo(Boolean.TRUE)
                .servico(Boolean.FALSE)
                .preco(BigDecimal.TEN)
                .build();

        Assertions.assertEquals("Produto 1", produto.getDescricao());
        Assertions.assertTrue(produto.getInativo());
        Assertions.assertFalse(produto.getServico());
        Assertions.assertEquals(BigDecimal.TEN, produto.getPreco());

        Produto produtoAtualizado = Produto.Builder.from(produto)
                .descricao("Produto Atualizado")
                .build();

        Assertions.assertEquals("Produto Atualizado", produtoAtualizado.getDescricao());
    }
}