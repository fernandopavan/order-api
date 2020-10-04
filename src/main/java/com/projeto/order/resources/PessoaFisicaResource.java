package com.projeto.order.resources;

import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.QPessoaFisica;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.services.PessoaFisicaService;
import com.projeto.order.services.exceptions.DataIntegrityException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/pessoas-fisicas")
public class PessoaFisicaResource {

    private final PessoaFisicaService service;
    private final PessoaFisicaRepository repository;

    public PessoaFisicaResource(PessoaFisicaService service, PessoaFisicaRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ApiOperation("Busca uma pessoa física por Id")
    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisica> find(@PathVariable Long id) {
        PessoaFisica obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Busca uma pessoa física por nome")
    @GetMapping("/nome")
    public ResponseEntity<Iterable<PessoaFisica>> findByName(@RequestParam(value = "nome") String nome) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (nome != null && !nome.isEmpty()) {
            booleanBuilder.and(QPessoaFisica.pessoaFisica.nome.containsIgnoreCase(nome));
        }
        Iterable<PessoaFisica> pessoasFisicas = repository.findAll(booleanBuilder);
        return ResponseEntity.ok().body(pessoasFisicas);
    }

    @ApiOperation("Busca uma pessoa física por e-mail")
    @GetMapping("/email")
    public ResponseEntity<PessoaFisica> findByEmail(@RequestParam(value = "email") String email) {
        PessoaFisica obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Insere uma pessoa física")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<PessoaFisica> insert(@Valid @RequestBody PessoaFisica pessoaFisica) {
        PessoaFisica build = PessoaFisica.Builder.from(pessoaFisica).senha(pessoaFisica.getSenha()).build();
        return ResponseEntity.ok().body(repository.save(build));
    }

    @ApiOperation("Atualiza uma pessoa física")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody PessoaFisica pessoaFisica, @PathVariable Long id) {
        return ResponseEntity.ok().body(service.update(pessoaFisica, id));
    }

    @ApiOperation("Remove uma pessoa física")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            if (id.equals(1L)) {
                throw new DataIntegrityException("Não é possivel excluir a primeira pessoa física do sistema :( !");
            }
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há dados relacionados");
        }

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Retorna uma lista de pessoas paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pessoas paginada"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public ResponseEntity<Page<PessoaFisica>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "nome", defaultValue = "") String nome) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (nome != null && !nome.isEmpty()) {
            booleanBuilder.and(QPessoaFisica.pessoaFisica.nome.containsIgnoreCase(nome));
        }

        PageRequest pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.valueOf(direction), orderBy));

        Page<PessoaFisica> list = repository.findAll(booleanBuilder, pageable);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/test")
    public ResponseEntity<Page<PessoaFisica>> test(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @QuerydslPredicate(root = PessoaFisica.class) Predicate predicate) {

        PageRequest pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.valueOf(direction), orderBy));

        Page<PessoaFisica> list = repository.findAll(predicate, pageable);

        return ResponseEntity.ok().body(list);
    }
}
