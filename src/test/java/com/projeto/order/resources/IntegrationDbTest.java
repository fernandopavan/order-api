package com.projeto.order.resources;

import com.projeto.order.domain.PessoaFisica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationDbTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({"classpath:db_tests/schema.sql", "classpath:db_tests/insert_pessoa.sql"})
    @Test
    public void testGetById() {
        this.restTemplate.getForObject("http://localhost:" + port + "/api/pessoas-fisicas/1", PessoaFisica.class);
    }

}