package com.projeto.order.services.utils;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.QPessoaFisica;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.security.UserSS;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaFisicaRepository repository;

    public UserDetailsServiceImpl(PessoaFisicaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PessoaFisica> pessoaFisica = repository.findOne(QPessoaFisica.pessoaFisica.email.eq(email));

        if (!pessoaFisica.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(pessoaFisica.get().getId(), pessoaFisica.get().getEmail(), pessoaFisica.get().getSenha(), pessoaFisica.get().getPerfis());
    }
}
