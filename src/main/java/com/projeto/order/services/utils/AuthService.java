package com.projeto.order.services.utils;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.services.PessoaFisicaService;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    private final PessoaFisicaRepository repository;
    private final PessoaFisicaService service;
    private final BCryptPasswordEncoder pe;

    public AuthService(PessoaFisicaRepository repository, PessoaFisicaService service, BCryptPasswordEncoder pe) {
        this.repository = repository;
        this.service = service;
        this.pe = pe;
    }

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        PessoaFisica pessoaFisica = service.findByEmail(email);

        if (pessoaFisica == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();

        pessoaFisica = PessoaFisica.Builder.from(pessoaFisica)
                .senha(pe.encode(newPass))
                .build();

        repository.save(pessoaFisica);
        //emailService.sendNewPasswordEmail(pessoaFisica, newPass); envia para o e-mail
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um digito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else { // gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
