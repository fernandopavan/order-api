package com.projeto.order.services;

import com.projeto.order.domain.Pedido;
import com.projeto.order.domain.PedidoProduto;
import com.projeto.order.repositories.PedidoProdutoRepository;
import com.projeto.order.repositories.PedidoRepository;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    public PedidoService(PedidoRepository repository, ProdutoService produtoService, PedidoProdutoRepository pedidoProdutoRepository) {
        this.repository = repository;
        this.produtoService = produtoService;
        this.pedidoProdutoRepository = pedidoProdutoRepository;
    }

    public Pedido find(Long id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj) {
        Pedido pedido = repository.save(Pedido.Builder.create()
                .descricao(obj.getDescricao())
                .desconto(obj.getDesconto())
                .dataHora(LocalDateTime.now())
                .fechado(obj.getFechado())
                .build());

        for (PedidoProduto pp : obj.getPedidoProdutos()) {
            pp.setPedido(pedido);
            pp.setProduto(produtoService.find(pp.getProduto().getId()));
        }

        pedidoProdutoRepository.saveAll(obj.getPedidoProdutos());
        return obj;
    }

    public Pedido update(Pedido obj, Long id) {
        Pedido pedido = find(id);

        for (PedidoProduto pp : obj.getPedidoProdutos()) {
            pp.setProduto(produtoService.find(pp.getProduto().getId()));
        }

        repository.save(Pedido.Builder.from(pedido)
                .descricao(obj.getDescricao())
                .desconto(obj.getDesconto())
                .dataHora(LocalDateTime.now())
                .fechado(obj.getFechado())
                .pedidoProdutos(obj.getPedidoProdutos())
                .build());

//        pedidoProdutoRepository.saveAll(obj.getPedidoProdutos());
        return obj;
    }

}
