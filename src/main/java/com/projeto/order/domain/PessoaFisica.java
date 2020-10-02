package com.projeto.order.domain;

import com.projeto.order.domain.enums.Perfil;
import com.projeto.order.domain.pattern.AbstractEntity;
import com.projeto.order.domain.pattern.EntityBuilder;
import com.projeto.order.domain.validation.PessoaFisicaValid;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@PessoaFisicaValid
@SequenceGenerator(name = "SEQ_PESSOAS_FISICAS", sequenceName = "SEQ_PESSOAS_FISICAS", allocationSize = 1)
public class PessoaFisica extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOAS_FISICAS")
    private Long id;

    @NotEmpty(message = "Preenchimento do nome obrigatório")
    @Length(min = 3, max = 120, message = "O nome deve ter entre {min} e {max} caracteres")
    private String nome;

    @Email(message = "E-mail inválido")
    private String email;

    @NotEmpty(message = "Preenchimento da senha obrigatória")
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Integer> perfis = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public static final class Builder extends EntityBuilder<PessoaFisica> {

        public Builder(PessoaFisica entity, EntityBuilder.State state) {
            super(entity, state);
        }

        public static Builder create() {
            return new Builder(new PessoaFisica(), State.NEW);
        }

        public static Builder from(PessoaFisica entity) {
            return new Builder(entity, State.BUILT);
        }

        public Builder nome(String nome) {
            entity.nome = nome;
            return this;
        }

        public Builder email(String email) {
            entity.email = email;
            return this;
        }

        public Builder senha(String senha) {
            BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
            entity.senha = entity.id == null ? pe.encode(senha) : senha;
            return this;
        }

        public Builder perfis(Set<Perfil> perfis) {
            List<Integer> perfisId = perfis.stream().map(Perfil::getId).collect(Collectors.toList());
            entity.perfis.addAll(perfisId);
            return this;
        }
    }

}
