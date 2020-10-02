package com.projeto.order.services;

import com.projeto.order.domain.Pedido;
import com.projeto.order.repositories.PedidoRepository;
import com.projeto.order.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido find(Long id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido update(Pedido pedido, Long id) {
        Pedido entity = find(id);

        Pedido build = Pedido.Builder.from(entity)
                .build();

        return repository.save(build);
    }
}
