package com.projeto.order.domain;

import com.projeto.order.domain.enums.Perfil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class PessoaFisicaTest {

    @Test
    public void deveCriarUmaPessoaFisicaAPartirDoBuilder() {
        PessoaFisica pessoaFisica = PessoaFisica.Builder.create()
                .nome("Nome 1")
                .email("teste@gmail.com")
                .senha("123")
                .perfis(Collections.singleton(Perfil.ADMIN))
                .build();

        Assertions.assertEquals("Nome 1", pessoaFisica.getNome());
        Assertions.assertEquals("teste@gmail.com", pessoaFisica.getEmail());
        Assertions.assertEquals(Collections.singleton(Perfil.ADMIN), pessoaFisica.getPerfis());
    }

}