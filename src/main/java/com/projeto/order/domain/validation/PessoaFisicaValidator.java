package com.projeto.order.domain.validation;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class PessoaFisicaValidator implements ConstraintValidator<PessoaFisicaValid, PessoaFisica> {

    @Autowired
    private PessoaFisicaRepository repo;

    @Override
    public void initialize(PessoaFisicaValid ann) {
    }

    @Override
    public boolean isValid(PessoaFisica pessoaFisica, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        //TODO Rever: vem nulo ao rodar o DBService.class
        if (repo == null) {
            return true;
        }

        Integer id = pessoaFisica.getId();

        list.addAll(existsEmail(list, id, pessoaFisica.getEmail()));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private List<FieldMessage> existsEmail(List<FieldMessage> list, Integer id, String email) {
        PessoaFisica existsEmail = repo.findByEmail(email);

        if ((id == null && existsEmail != null) || (id != null && existsEmail != null && !id.equals(existsEmail.getId()))) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        return list;
    }
}

