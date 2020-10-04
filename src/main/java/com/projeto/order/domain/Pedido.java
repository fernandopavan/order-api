package com.projeto.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.order.domain.pattern.AbstractEntity;
import com.projeto.order.domain.pattern.EntityBuilder;
import com.projeto.order.domain.validation.PedidoValid;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@PedidoValid
@Table(name = "PEDIDOS")
@SequenceGenerator(name = "SEQ_PEDIDOS", sequenceName = "SEQ_PEDIDOS", allocationSize = 1)
public class Pedido extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDOS")
    private Long id;

    @NotEmpty(message = "Preenchimento da descrição é obrigatório")
    @Length(min = 3, max = 120, message = "A descrição deve ter entre {min} e {max} caracteres")
    private String descricao;

    //TODO momento criacao
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataHora;

    private BigDecimal desconto;

    private BigDecimal valorTotal;

    //    @NotEmpty(message = "Preenchimento de produtos do pedido é obrigatório")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PedidoProduto> pedidoProdutos = new HashSet<>();

    private Boolean fechado;

    @Override
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Set<PedidoProduto> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public Boolean getFechado() {
        return fechado != null && fechado;
    }

    public static final class Builder extends EntityBuilder<Pedido> {

        public Builder(Pedido entity, EntityBuilder.State state) {
            super(entity, state);
        }

        public static Builder create() {
            return new Builder(new Pedido(), State.NEW);
        }

        public static Builder from(Pedido entity) {
            return new Builder(entity, State.BUILT);
        }

        public Builder descricao(String descricao) {
            entity.descricao = descricao;
            return this;
        }

        public Builder dataHora(LocalDateTime dataHora) {
            entity.dataHora = dataHora;
            return this;
        }

        public Builder desconto(BigDecimal desconto) {
            entity.desconto = desconto;
            return this;
        }

        public Builder valorTotal(BigDecimal valorTotal) {
            entity.valorTotal = valorTotal;
            BigDecimal desconto = BigDecimal.ZERO;

            if (!entity.fechado && !entity.desconto.equals(BigDecimal.ZERO)) {
                desconto = entity.valorTotal.multiply(entity.desconto.divide(BigDecimal.valueOf(100)));
            }

            entity.valorTotal = entity.valorTotal.subtract(desconto);

            return this;
        }

        public Builder fechado(Boolean fechado) {
            entity.fechado = fechado;
            return this;
        }

        public Builder pedidoProdutos(Collection<PedidoProduto> pedidoProdutos) {
            entity.pedidoProdutos.clear();
            entity.pedidoProdutos.addAll(pedidoProdutos);
            entity.pedidoProdutos.forEach(value -> value.setPedido(entity));
            return this;
        }

    }

}
