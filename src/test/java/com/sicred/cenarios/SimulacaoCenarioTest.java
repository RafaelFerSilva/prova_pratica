package com.sicred.cenarios;

import com.sicred.config.Configuracoes;
import com.sicred.factory.SimulacaoDataFactory;
import com.sicred.pojo.Simulacao;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SimulacaoCenarioTest {
    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }


    @Test
    public void testTentarInserirUmaSimulacaoComCpfQueJaExiste() throws IOException {

        Simulacao simulacaoCpfSemRestricao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoCpfSemRestricao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoCpfSemRestricao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(409);
    }


}
