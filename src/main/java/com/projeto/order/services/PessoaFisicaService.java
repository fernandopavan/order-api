package com.projeto.order.services;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.QPessoaFisica;
import com.projeto.order.domain.enums.Perfil;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.security.UserSS;
import com.projeto.order.services.exceptions.AuthorizationException;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import com.projeto.order.services.utils.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaFisicaService {

    private final PessoaFisicaRepository repository;

    public PessoaFisicaService(PessoaFisicaRepository repository) {
        this.repository = repository;
    }

    public PessoaFisica find(UUID id) {
        Optional<PessoaFisica> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + PessoaFisica.class.getName()));
    }

    public PessoaFisica findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<PessoaFisica> obj = repository.findOne(QPessoaFisica.pessoaFisica.email.eq(email));

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + PessoaFisica.class.getName()));
    }

    public PessoaFisica update(PessoaFisica pessoaFisica, UUID id) {
        PessoaFisica entity = find(id);

        PessoaFisica build = PessoaFisica.Builder.from(entity)
                .nome(pessoaFisica.getNome())
                .email(pessoaFisica.getEmail())
                .senha(pessoaFisica.getSenha())
                .perfis(pessoaFisica.getPerfis())
                .build();

        return repository.save(build);
    }
}
