package com.projeto.order.domain.validation;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.QPessoaFisica;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PessoaFisicaValidator implements ConstraintValidator<PessoaFisicaValid, PessoaFisica> {

    @Autowired
    private PessoaFisicaRepository repository;

    @Override
    public void initialize(PessoaFisicaValid ann) {
    }

    @Override
    public boolean isValid(PessoaFisica pessoaFisica, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (repository == null) {
            return true;
        }

        UUID id = pessoaFisica.getId();

        list.addAll(existsEmail(list, id, pessoaFisica.getEmail()));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private List<FieldMessage> existsEmail(List<FieldMessage> list, UUID id, String email) {
        PessoaFisica existsEmail = repository.findOne(QPessoaFisica.pessoaFisica.email.eq(email)).orElse(null);

        if ((id == null && existsEmail != null) || (id != null && existsEmail != null && !id.equals(existsEmail.getId()))) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        return list;
    }
}

