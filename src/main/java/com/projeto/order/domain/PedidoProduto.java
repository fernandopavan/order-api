package com.projeto.order.domain;

import com.projeto.order.domain.pattern.AbstractEntity;
import com.projeto.order.domain.pattern.EntityBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PEDIDOS_PRODUTOS")
@SequenceGenerator(name = "SEQ_PEDIDOS_PRODUTOS", sequenceName = "SEQ_PEDIDOS_PRODUTOS", allocationSize = 1)
public class PedidoProduto extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDOS_PRODUTOS")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "O enduro regularidade é obrigatório")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "O produto é obrigatório")
    private Produto produto;

    @NotNull(message = "A quantidade é obrigatório")
    private Integer quantidade;

    @Override
    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public static final class Builder extends EntityBuilder<PedidoProduto> {

        public Builder(PedidoProduto entity, EntityBuilder.State state) {
            super(entity, state);
        }

        public static Builder create() {
            return new Builder(new PedidoProduto(), State.NEW);
        }

        public static Builder from(PedidoProduto entity) {
            return new Builder(entity, State.BUILT);
        }

        public Builder pedido(Pedido pedido) {
            entity.pedido = pedido;
            return this;
        }

        public Builder produto(Produto produto) {
            entity.produto = produto;
            return this;
        }

        public Builder quantidade(Integer quantidade) {
            entity.quantidade = quantidade;
            return this;
        }

    }
}
