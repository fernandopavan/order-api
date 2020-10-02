package com.projeto.order.resources;

import com.projeto.order.domain.Produto;
import com.projeto.order.domain.QProduto;
import com.projeto.order.repositories.ProdutoRepository;
import com.projeto.order.services.ProdutoService;
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
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    private final ProdutoService service;
    private final ProdutoRepository repository;

    public ProdutoResource(ProdutoService service, ProdutoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ApiOperation("Busca um produto por Id")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> find(@PathVariable Long id) {
        Produto obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Insere um produto")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<Produto> insert(@Valid @RequestBody Produto produto) {
        return ResponseEntity.ok().body(repository.save(produto));
    }

    @ApiOperation("Atualiza um produto")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody Produto produto, @PathVariable Long id) {
        return ResponseEntity.ok().body(service.update(produto, id));
    }

    @ApiOperation("Remove um produto")
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

    @ApiOperation(value = "Retorna uma lista de produtos paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos paginada"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<Page<Produto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "descricao", defaultValue = "") String descricao) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (descricao != null && !descricao.isEmpty()) {
            booleanBuilder.and(QProduto.produto.descricao.containsIgnoreCase(descricao));
        }

        PageRequest pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.valueOf(direction), orderBy));

        Page<Produto> list = repository.findAll(booleanBuilder, pageable);

        return ResponseEntity.ok().body(list);
    }

}
