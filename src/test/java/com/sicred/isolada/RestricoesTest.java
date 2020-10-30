package com.sicred.isolada;

import com.sicred.config.Configuracoes;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

public class RestricoesTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testConsultaUmCpfQuePossuiRestricao() {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/restricoes/55856777050")
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("mensagem", equalTo("O CPF 55856777050 possui restrição"));

    }

    @Test
    public void testConsultaUmCpfQueNaoPossuiRestricao() {

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/restricoes/7489657485")
        .then()
            .assertThat()
            .statusCode(204);

    }
}
