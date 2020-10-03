package com.projeto.order.resources;

import com.projeto.order.domain.Pedido;
import com.projeto.order.domain.QPedido;
import com.projeto.order.repositories.PedidoRepository;
import com.projeto.order.services.PedidoService;
import com.projeto.order.services.exceptions.DataIntegrityException;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService service;
    private final PedidoRepository repository;

    public PedidoResource(PedidoService service, PedidoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ApiOperation("Busca um pedido por Id")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Long id) {
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Insere um pedido")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido pedido) {
        ;
        return ResponseEntity.ok().body(service.insert(pedido));
    }

    @ApiOperation("Atualiza um pedido")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody Pedido pedido, @PathVariable Long id) {
        return ResponseEntity.ok().body(service.update(pedido, id));
    }

    @ApiOperation("Remove um pedido")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há dados relacionados");
        }

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Retorna uma lista de pedidos paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pedidos paginada"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<Page<Pedido>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "orderBy", defaultValue = "descricao") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "descricao", defaultValue = "") String descricao) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (descricao != null && !descricao.isEmpty()) {
            booleanBuilder.and(QPedido.pedido.descricao.containsIgnoreCase(descricao));
        }

        PageRequest pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.valueOf(direction), orderBy));

        Page<Pedido> list = repository.findAll(booleanBuilder, pageable);

        return ResponseEntity.ok().body(list);
    }

}
