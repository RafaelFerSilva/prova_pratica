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

public class AlterarSimulacaoCenarioTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCpfDeUmaSimulacaoNaoPodeSerAlteradoParaVazio() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf("");
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());


        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(404)
                .body("mensagem", equalTo(simulacao.getCpf() + "CPF não pode ser vazio"));
    }

    @Test
    public void testAlterarNomeDeUmaSimulacao() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome("Rafael Fernandes");
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());


        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("nome", equalTo("Rafael Fernandes"));
    }

    @Test
    public void testAlterarEmailDeUmaSimulacao() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail("rafael@email.com");
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());


        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("email", equalTo("rafael@email.com"));
    }

    @Test
    public void testSimulacaoNaoPodeSerAlterarParaUmEmailInvalido() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail("sdyrhytrytrytryrt");
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());


        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("email", equalTo("E-mail deve ser um e-mail válido"));
    }

    @Test
    public void testAlterarOValorDeUmaSimulacao() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(40000);

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("valor", equalTo(40000));
    }

    @Test
    public void testAlterarParcelaDeUmaSimulacaoParaValorValido() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(6);
        novaSimulacao.setValor(simulacao.getValor());

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("parcelas", equalTo(novaSimulacao.getParcelas()));
    }

    @Test
    public void testParcelaDeUmaSimulacaoNaoPodeSerAlteradaParaValorMenorQue2() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(1);
        novaSimulacao.setValor(simulacao.getValor());

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(400)
                .body("erros.parcelas", equalTo("Parcelas deve ser igual ou maior que 2"));
    }

    @Test
    public void testParcelaDeUmaSimulacaoNaoPodeSerAlteradaParaValorMaiorQue48() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
            .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(simulacao.getSeguro());
        novaSimulacao.setParcelas(49);
        novaSimulacao.setValor(simulacao.getValor());

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void testAlterarOSeguroDeUmaSimulacaoDeTrueParaFalse() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(false);
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200)
                .body("seguro", equalTo(false));
    }

    @Test
    public void testAlterarOSeguroDeUmaSimulacaoDeFalseParaTrue() throws IOException {

        Simulacao simulacao = SimulacaoDataFactory.criarSimulacaoValida();

        simulacao.setSeguro(false);

        given()
            .contentType(ContentType.JSON)
            .body(simulacao)
        .when()
            .post("/simulacoes/")
        .then()
            .assertThat()
                .log().all()
                .statusCode(201);

        Simulacao novaSimulacao = SimulacaoDataFactory.criarSimulacaoValida();
        novaSimulacao.setNome(simulacao.getNome());
        novaSimulacao.setCpf(simulacao.getCpf());
        novaSimulacao.setEmail(simulacao.getEmail());
        novaSimulacao.setSeguro(true);
        novaSimulacao.setParcelas(simulacao.getParcelas());
        novaSimulacao.setValor(simulacao.getValor());

        given()
            .contentType(ContentType.JSON)
            .body(novaSimulacao)
        .when()
            .put("/simulacoes/" + simulacao.getCpf())
        .then()
            .assertThat()
                .log().all()
                .statusCode(200);
    }
}
