package com.projeto.order.services.utils;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.security.UserSS;
import com.projeto.order.services.PessoaFisicaService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaFisicaService service;

    public UserDetailsServiceImpl(PessoaFisicaService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PessoaFisica pessoaFisica = service.findByEmail(email);

        if (pessoaFisica == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(pessoaFisica.getId(), pessoaFisica.getEmail(), pessoaFisica.getSenha(), pessoaFisica.getPerfis());
    }
}
