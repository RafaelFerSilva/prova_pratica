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
import java.util.Random;

public class ConsultarSimulacaoTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testConsultarTodasAsSimulaçoesCadastradas()  {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/simulacoes")
        .then()
            .assertThat()
                .statusCode(200);

    }

    @Test
    public void testConsultarUmaSimulacaoPeloCPF() {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/simulacoes/66414919004")
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(11))
                .body("nome", equalTo("Fulano"))
                .body("email", equalTo("fulano@gmail.com"))
                .body("valor", equalTo(11000.00f))
                .body("parcelas", equalTo(3))
                .body("seguro", equalTo(true));

    }

    @Test
    public void testConsultarUmaSimulacaoQueNãoExistePeloCPF() {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/simulacoes/58741214785")
        .then()
            .assertThat()
                .statusCode(404)
                .body("mensagem", equalTo("O CPF 58741214785 possui restrição"));

    }

}
