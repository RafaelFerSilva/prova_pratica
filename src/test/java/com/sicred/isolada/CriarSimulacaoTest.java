package com.sicred.isolada;

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
import static org.hamcrest.Matchers.*;

public class CriarSimulacaoTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testInserirUmSimulacaoComCpfSemRestricao() throws IOException {

        Simulacao simulacaoCpfSemRestricao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoCpfSemRestricao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201)
                .body("nome", equalTo(simulacaoCpfSemRestricao.getNome()))
                .body("email", equalTo(simulacaoCpfSemRestricao.getEmail()))
                .body("valor", equalTo(simulacaoCpfSemRestricao.getValor()))
                .body("parcelas", equalTo(simulacaoCpfSemRestricao.getParcelas()))
                .body("seguro", equalTo(simulacaoCpfSemRestricao.getSeguro()));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmCPFNull() throws IOException {

        Simulacao simulacaoComCpfNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComCpfNull.setCpf(null);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComCpfNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.cpf", equalTo("CPF não pode ser vazio"));
    }

    @Test
    public void testTentarInserirUmaSimulacaoComCpfEmBranco() throws IOException {

        Simulacao simulacaoComCpfEmBranco = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComCpfEmBranco.setCpf("");

        given()
                .contentType(ContentType.JSON)
                .body(simulacaoComCpfEmBranco)
                .when()
                .post("/simulacoes/")
                .then()
                .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.cpf", equalTo("CPF não pode ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmNomeNull() throws IOException {

        Simulacao simulacaoComNomeNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComNomeNull.setNome(null);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComNomeNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.nome", equalTo("Nome não pode ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmNomeEmBranco() throws IOException {

        Simulacao simulacaoComNomeEmBranco = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComNomeEmBranco.setNome("");

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComNomeEmBranco)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.nome", equalTo("Nome não pode ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmEmailNull() throws IOException {

        Simulacao simulacaoComEmailNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComEmailNull.setEmail(null);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComEmailNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.email", equalTo("E-mail não deve ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmEmailInvalido() throws IOException {

        Simulacao simulacaoComEmailNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComEmailNull.setEmail("asadsaferfsgsfgfdtregf");

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComEmailNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.email", equalTo("E-mail deve ser um e-mail válido"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmEmaiEmBranco() throws IOException {

        Simulacao simulacaoComEmailNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComEmailNull.setEmail("");

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComEmailNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.email", equalTo("E-mail deve ser um e-mail válido"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmValorNull() throws IOException {

        Simulacao simulacaoComValorNull = SimulacaoDataFactory.criarSimulacaoValida();
        simulacaoComValorNull.setValor(null);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValorNull)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.valor", equalTo("Valor não pode ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmValorMenorQue1000() throws IOException {

        Simulacao simulacaoComValor = SimulacaoDataFactory.criarSimulacaoValida();
        Random rand = new Random();

        simulacaoComValor.setValor((Number) rand.nextInt(999));

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValor)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.valor", equalTo("Valor não pode ser menor que 1000"));
    }

    @Test
    public void testUmaSimulacaoTemQueTerUmValorMinimoDe1000() throws IOException {

        Simulacao simulacaoComValor = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoComValor.setValor(1000);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValor)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201)
                .body("nome", equalTo(simulacaoComValor.getNome()))
                .body("email", equalTo(simulacaoComValor.getEmail()))
                .body("valor", equalTo(simulacaoComValor.getValor()))
                .body("parcelas", equalTo(simulacaoComValor.getParcelas()))
                .body("seguro", equalTo(simulacaoComValor.getSeguro()));
    }

    @Test
    public void testUmaSimulacaoTemQueTerUmValorMaiorQue999EMenorQue40000() throws IOException {

        Simulacao simulacaoComValor = SimulacaoDataFactory.criarSimulacaoValida();
        Random rand = new Random();

        simulacaoComValor.setValor((Number) rand.nextInt((40000 - 1000)));

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValor)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201)
                .body("nome", equalTo(simulacaoComValor.getNome()))
                .body("email", equalTo(simulacaoComValor.getEmail()))
                .body("valor", equalTo(simulacaoComValor.getValor()))
                .body("parcelas", equalTo(simulacaoComValor.getParcelas()))
                .body("seguro", equalTo(simulacaoComValor.getSeguro()));
    }

    @Test
    public void testUmaSimulacaoTemQueTerUmValorMaximoDe40000() throws IOException {

        Simulacao simulacaoComValor = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoComValor.setValor(40000);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValor)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201)
                .body("nome", equalTo(simulacaoComValor.getNome()))
                .body("email", equalTo(simulacaoComValor.getEmail()))
                .body("valor", equalTo(simulacaoComValor.getValor()))
                .body("parcelas", equalTo(simulacaoComValor.getParcelas()))
                .body("seguro", equalTo(simulacaoComValor.getSeguro()));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmValorMaiorQue40000() throws IOException {

        Simulacao simulacaoComValor = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoComValor.setValor(40001);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoComValor)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.valor", equalTo("Valor deve ser menor ou igual a R$ 40.000"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmaParcelaNull() throws IOException {

        Simulacao simulacaoParcela = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoParcela.setParcelas(null);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoParcela)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.parcelas", equalTo("Parcelas não pode ser vazio"));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerUmaParcelaMenorQue2() throws IOException {

        Simulacao simulacaoParcela = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoParcela.setParcelas(1);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoParcela)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.parcelas", equalTo("Parcelas deve ser igual ou maior que 2"));
    }

    @Test
    public void testUmaSimulacaoTemAParcelaMinimaDe2() throws IOException {

        Simulacao simulacaoParcela = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoParcela.setParcelas(2);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoParcela)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201)
                .body("nome", equalTo(simulacaoParcela.getNome()))
                .body("email", equalTo(simulacaoParcela.getEmail()))
                .body("valor", equalTo(simulacaoParcela.getValor()))
                .body("parcelas", equalTo(simulacaoParcela.getParcelas()))
                .body("seguro", equalTo(simulacaoParcela.getSeguro()));
    }

    @Test
    public void testUmaSimulacaoNaoPodeTerAParcelaMaiorQue48() throws IOException {

        Simulacao simulacaoParcela = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoParcela.setParcelas(49);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoParcela)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void testUmaSimulacaoPodeTerUmSeguroTrue() throws IOException {

        Simulacao simulacaoSeguro = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoSeguro.setSeguro(true);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoSeguro)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void testUmaSimulacaoPodeTerUmSeguroFalse() throws IOException {

        Simulacao simulacaoSeguro = SimulacaoDataFactory.criarSimulacaoValida();

        simulacaoSeguro.setSeguro(false);

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoSeguro)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);
    }

    //Criar tests para null e "" para os demais valores
}
