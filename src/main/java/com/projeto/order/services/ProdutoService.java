package com.projeto.order.services;

import com.projeto.order.domain.Produto;
import com.projeto.order.repositories.ProdutoRepository;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto find(UUID id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Produto update(Produto produto, UUID id) {
        Produto entity = find(id);

        Produto build = Produto.Builder.from(entity)
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .servico(produto.getServico())
                .inativo(produto.getInativo())
                .build();

        return repository.save(build);
    }
}
