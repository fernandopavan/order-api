package com.projeto.order.domain.validation;

import com.projeto.order.domain.Pedido;
import com.projeto.order.domain.PedidoProduto;
import com.projeto.order.domain.Produto;
import com.projeto.order.domain.QProduto;
import com.projeto.order.repositories.ProdutoRepository;
import com.projeto.order.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoValidator implements ConstraintValidator<PedidoValid, Pedido> {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void initialize(PedidoValid ann) {
    }

    @Override
    public boolean isValid(Pedido pedido, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (produtoRepository == null) {
            return true;
        }

        list.addAll(existsProdutoInativo(list, pedido.getPedidoProdutos()));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private List<FieldMessage> existsProdutoInativo(List<FieldMessage> list, Collection<PedidoProduto> pedidoProdutos) {
        List<Long> produtosIds = pedidoProdutos.stream().map(PedidoProduto::getProduto).map(Produto::getId).collect(Collectors.toList());

        boolean existsProdutoInativo = produtoRepository.exists(QProduto.produto.id.in(produtosIds).and(QProduto.produto.inativo.isTrue()));

        if (existsProdutoInativo) {
            list.add(new FieldMessage("pedidoProdutos", "Não é possível adicionar produtos inativos em seu pedido."));
        }

        return list;
    }
}

