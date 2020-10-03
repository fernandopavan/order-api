package com.projeto.order.services;

import com.projeto.order.domain.Produto;
import com.projeto.order.domain.QPedidoProduto;
import com.projeto.order.repositories.PedidoProdutoRepository;
import com.projeto.order.repositories.ProdutoRepository;
import com.projeto.order.services.exceptions.DataIntegrityException;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    public ProdutoService(ProdutoRepository repository, PedidoProdutoRepository pedidoProdutoRepository) {
        this.repository = repository;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
    }

    public Produto find(Long id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public void delete(Long id) {
        //TODO fazer com bean ?
        if (pedidoProdutoRepository.exists(QPedidoProduto.pedidoProduto.produto.id.eq(id))) {
            throw new DataIntegrityException("Não é possivel excluir este produto, pois o mesmo esta vinculado a um pedido!");
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há dados relacionados");
        }
    }

    public Produto update(Produto produto, Long id) {
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
