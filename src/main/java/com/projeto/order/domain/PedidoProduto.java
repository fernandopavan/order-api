package com.projeto.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.order.domain.pattern.AbstractEntity;
import com.projeto.order.domain.pattern.EntityBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "PEDIDOS_PRODUTOS")
public class PedidoProduto extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "O pedido é obrigatório")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "O produto é obrigatório")
    private Produto produto;

    @NotNull(message = "A quantidade é obrigatório")
    private Integer quantidade;

    @Override
    public UUID getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
