package com.sicred.isolada;

import com.sicred.config.Configuracoes;
import com.sicred.factory.SimulacaoDataFactory;
import com.sicred.pojo.Simulacao;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AlterarSimulacaoTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testTentarAlterarOValorDeUmaSimulacaoQueNaoExiste() throws IOException {

        Simulacao SimulacaoQueNaoExiste = SimulacaoDataFactory.criarSimulacaoValida();

        SimulacaoQueNaoExiste.setCpf("7459684521");

        given()
            .contentType(ContentType.JSON)
            .body(SimulacaoQueNaoExiste)
        .when()
            .put("/simulacoes/" + SimulacaoQueNaoExiste.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(404)
                .body("mensagem", equalTo("CPF " + SimulacaoQueNaoExiste.getCpf() + " não encontrado"));
    }
}
