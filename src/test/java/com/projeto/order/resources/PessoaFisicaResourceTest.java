package com.projeto.order.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.order.domain.PessoaFisica;
import com.projeto.order.domain.enums.Perfil;
import com.projeto.order.repositories.PessoaFisicaRepository;
import com.projeto.order.services.PessoaFisicaService;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
@SpringBootTest
class PessoaFisicaResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PessoaFisicaService service;

    @MockBean
    private PessoaFisicaRepository repository;

//    @Test
//    void deveBuscarPeloId() throws Exception {
//        PessoaFisica pessoaFisica = getPessoaCriada();
//
//        when(service.find(any())).thenReturn(pessoaFisica);
//
//        String expected = "{\"createdIn\":null,\"updatedIn\":null,\"version\":null,\"id\":null,\"nome\":\"Nome 1\",\"email\":" +
//                "\"teste@gmail.com\",\"senha\":null,\"perfis\":[\"ADMIN\"]}";
//
//        mvc.perform(get("/pessoas-fisicas/1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(expected));
//
//        verify(service, times(1)).find(UUID.randomUUID());
//    }
//
//    @Test
////    @WithUserDetails(value = "root")
//    void deveInserir() throws Exception {
//        PessoaFisica pessoa = getPessoaCriada();
////        when(repository.save(any(PessoaFisica.class))).thenReturn(pessoa);
//
//        mvc.perform(post("/pessoas-fisicas")
//                .content(objectMapper.writeValueAsString(pessoa))
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(repository, times(1)).save(eq(pessoa));
//    }
//
//    @Test
//    void deveAtualizar() throws Exception {
//        PessoaFisica pessoa = getPessoaCriada();
//
//        when(repository.findById(UUID.randomUUID())).thenReturn(java.util.Optional.ofNullable(pessoa));
//        when(repository.save(any(PessoaFisica.class))).thenReturn(pessoa);
//
//        String send = "{\"nome\":\"Nome 1\",\"email\":" +
//                "\"teste@gmail.com\",\"senha\":\"123\",\"perfis\":[\"ADMIN\"]}";
//
//        mvc.perform(put("/pessoas-fisicas/1")
//                .content(send)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(repository, times(1)).save(any(PessoaFisica.class));
//    }
//
//    @Test
//    void deveExcluir() throws Exception {
//        PessoaFisica pessoa = getPessoaCriada();
//
//        mvc.perform(delete("/pessoas-fisicas/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(repository, times(1)).deleteById(any());
//    }

    @Test
    void deveRetornarBuscaPaginada() throws Exception {
        Page<PessoaFisica> pagedResponse = new PageImpl(Collections.singletonList(getPessoaCriada()));
        when(repository.findAll(any(Predicate.class), any(PageRequest.class))).thenReturn(pagedResponse);

        String expectedContent = "{\"content\":[{\"createdIn\":null,\"updatedIn\":null,\"version\":null,\"id\":null,\"nome\":\"Nome 1\"," +
                "\"email\":\"teste@gmail.com\",\"senha\":null,\"perfis\":[\"ADMIN\"]}],\"pageable\":\"INSTANCE\"";

        mvc.perform(get("/pessoas-fisicas")
                .param("page", "0")
                .param("limit", "2")
                .param("orderBy", "nome")
                .param("direction", "DESC")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedContent)));

        verify(repository, times(1)).findAll(any(Predicate.class), any(PageRequest.class));
    }

    private PessoaFisica getPessoaCriada() {
        return PessoaFisica.Builder.create()
                .nome("Nome 1")
                .email("teste@gmail.com")
                .perfis(Collections.singleton(Perfil.ADMIN))
                .build();
    }

}