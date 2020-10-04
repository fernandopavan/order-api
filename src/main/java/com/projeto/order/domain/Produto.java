package com.projeto.order.domain;

import com.projeto.order.domain.pattern.AbstractEntity;
import com.projeto.order.domain.pattern.EntityBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTOS")
@SequenceGenerator(name = "SEQ_PRODUTOS", sequenceName = "SEQ_PRODUTOS", allocationSize = 1)
public class Produto extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUTOS")
    private Long id;

    @NotEmpty(message = "Preenchimento da descrição é obrigatório")
    @Length(min = 3, max = 120, message = "A descrição deve ter entre {min} e {max} caracteres")
    private String descricao;

    @NotNull(message = "Preenchimento do preço é obrigatório")
    private BigDecimal preco;

    private Boolean servico;

    private Boolean inativo;

    @Override
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Boolean getServico() {
        return servico != null && servico;
    }

    public Boolean getInativo() {
        return inativo != null && inativo;
    }

    public static final class Builder extends EntityBuilder<Produto> {

        public Builder(Produto entity, EntityBuilder.State state) {
            super(entity, state);
        }

        public static Builder create() {
            return new Builder(new Produto(), State.NEW);
        }

        public static Builder from(Produto entity) {
            return new Builder(entity, State.BUILT);
        }

        public Builder descricao(String descricao) {
            entity.descricao = descricao;
            return this;
        }

        public Builder preco(BigDecimal preco) {
            entity.preco = preco;
            return this;
        }

        public Builder servico(Boolean servico) {
            entity.servico = servico;
            return this;
        }

        public Builder inativo(Boolean inativo) {
            entity.inativo = inativo;
            return this;
        }

    }
}
