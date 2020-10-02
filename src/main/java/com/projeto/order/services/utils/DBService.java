package com.projeto.order.services.utils;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.enums.Perfil;
import com.projeto.order.repositories.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DBService {

    private final PessoaFisicaRepository repository;

    public DBService(PessoaFisicaRepository repository) {
        this.repository = repository;
    }

    public void instantiateTestDatabase() {
        PessoaFisica pessoaFisica = PessoaFisica.Builder.create()
                .nome("Maria de Aparecida")
                .email("maria@gmail.com")
                .senha("123")
                .perfis(Collections.singleton(Perfil.ADMIN))
                .build();

        repository.save(pessoaFisica);
    }
}
