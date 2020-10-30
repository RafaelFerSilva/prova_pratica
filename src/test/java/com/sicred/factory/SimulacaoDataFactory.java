package com.sicred.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicred.pojo.Simulacao;
import org.apache.commons.lang.RandomStringUtils;
import java.io.FileInputStream;
import java.io.IOException;

public class SimulacaoDataFactory {

    public static Simulacao criarSimulacao() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        Simulacao simulacao = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Simulacao.json"), Simulacao.class);

        return simulacao;
    }

    public static Simulacao criarSimulacaoValida() throws IOException {
        Simulacao simulacaoValida = criarSimulacao();
        simulacaoValida.setCpf(RandomStringUtils.randomNumeric(11));
        return simulacaoValida;
    }

}
