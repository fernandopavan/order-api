package com.projeto.order.domain.validation;

import com.projeto.order.domain.Produto;
import com.projeto.order.domain.QPedidoProduto;
import com.projeto.order.repositories.PedidoProdutoRepository;
import com.projeto.order.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProdutoValidator implements ConstraintValidator<ProdutoValid, Produto> {

    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;

    @Override
    public void initialize(ProdutoValid ann) {
    }

    @Override
    public boolean isValid(Produto produto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (pedidoProdutoRepository == null) {
            return true;
        }

        list.addAll(produtoEmUso(list, produto.getId()));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private List<FieldMessage> produtoEmUso(List<FieldMessage> list, UUID id) {
        if (id == null) {
            return list;
        }

        if (pedidoProdutoRepository.exists(QPedidoProduto.pedidoProduto.produto.id.eq(id))) {
            list.add(new FieldMessage("pedidoProdutos", "Não é possivel excluir este produto, pois o mesmo esta vinculado a um pedido!"));
        }

        return list;
    }
}

