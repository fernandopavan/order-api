package com.projeto.order.services;

import com.google.common.collect.ImmutableSet;
import com.projeto.order.domain.Pedido;
import com.projeto.order.domain.PedidoProduto;
import com.projeto.order.domain.Produto;
import com.projeto.order.repositories.PedidoProdutoRepository;
import com.projeto.order.repositories.PedidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService service;

    @MockBean
    private PedidoRepository repository;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private PedidoProdutoRepository pedidoProdutoRepository;

    @Test
    void deveInserirPedidoComProdutosEServicos() {
        Produto produto = getMockProduto();
        PedidoProduto pedidoProduto = PedidoProduto.Builder.create()
                .quantidade(2)
                .produto(produto)
                .build();

        Produto servico = getMockServico();
        PedidoProduto pedidoServico = PedidoProduto.Builder.create()
                .quantidade(1)
                .produto(servico)
                .build();

        Set<PedidoProduto> pedidoProdutos = ImmutableSet.of(pedidoProduto, pedidoServico);

        Pedido pedido = createPedido(pedidoProdutos);

        when(repository.save(any(Pedido.class))).thenReturn(pedido);
        when(produtoService.find(any())).thenReturn(produto, servico);
        when(pedidoProdutoRepository.saveAll(anyCollection())).thenReturn(pedidoProdutos);

        Pedido save = service.insert(pedido);

        Assertions.assertEquals("Pedido 1", save.getDescricao());
        Assertions.assertFalse(save.getFechado());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), save.getDataHora());
        Assertions.assertEquals(BigDecimal.TEN, save.getDesconto());
        Assertions.assertEquals(BigDecimal.valueOf(19.0).setScale(1), save.getValorTotal());
        Assertions.assertEquals(2, save.getPedidoProdutos().size());
    }

    @Test
    void deveAtualizarPedidoComProdutosEServicos() {
        Produto servico = getMockServico();
        PedidoProduto pedidoServico = PedidoProduto.Builder.create()
                .quantidade(1)
                .produto(servico)
                .build();

        Produto produto = getMockProduto();
        PedidoProduto pedidoProduto = PedidoProduto.Builder.create()
                .quantidade(3)
                .produto(produto)
                .build();

        Set<PedidoProduto> pedidoProdutos = ImmutableSet.of(pedidoProduto, pedidoServico);
        Pedido pedidoUpdated = createPedido(pedidoProdutos);

        when(repository.findById(any())).thenReturn(Optional.of(createPedido(Collections.EMPTY_SET)));
        when(produtoService.find(any())).thenReturn(produto, servico);
        when(repository.save(any(Pedido.class))).thenReturn(pedidoUpdated);

        Pedido save = service.update(pedidoUpdated, UUID.randomUUID());

        Assertions.assertEquals("Pedido 1", save.getDescricao());
        Assertions.assertFalse(save.getFechado());
        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1), save.getDataHora());
        Assertions.assertEquals(BigDecimal.TEN, save.getDesconto());
        Assertions.assertEquals(BigDecimal.valueOf(28.0).setScale(1), save.getValorTotal());
        Assertions.assertEquals(2, save.getPedidoProdutos().size());
    }

    private Pedido createPedido(Set<PedidoProduto> pedidoProdutos) {
        return Pedido.Builder.create()
                .descricao("Pedido 1")
                .fechado(Boolean.FALSE)
                .dataHora(LocalDateTime.of(2020, 1, 1, 1, 1))
                .desconto(BigDecimal.TEN)
                .pedidoProdutos(pedidoProdutos)
                .valorTotal(pedidoProdutos)
                .build();
    }

    private Produto getMockServico() {
        Produto servico = mock(Produto.class);
        when(servico.getId()).thenReturn(UUID.randomUUID());
        when(servico.getDescricao()).thenReturn("Servico 1");
        when(servico.getServico()).thenReturn(Boolean.TRUE);
        when(servico.getInativo()).thenReturn(Boolean.FALSE);
        when(servico.getPreco()).thenReturn(BigDecimal.ONE);
        return servico;
    }

    private Produto getMockProduto() {
        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(UUID.randomUUID());
        when(produto.getDescricao()).thenReturn("Produto 1");
        when(produto.getServico()).thenReturn(Boolean.FALSE);
        when(produto.getInativo()).thenReturn(Boolean.FALSE);
        when(produto.getPreco()).thenReturn(BigDecimal.TEN);
        return produto;
    }

}