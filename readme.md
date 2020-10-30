## Como executar os testes

Dado que a API (aplicação) esteja sendo executada. (A aplicação estará disponível através da URL [http://localhost:8080](http://localhost:8080))

Na raiz do projeto, através de seu Prompt de Commando/Terminal/Console execute o comando 

```bash
mvn surefire:test
```

## Relatório dos tests - Report

Utilizamos o Allure (https://docs.qameta.io/allure/) para gerar o report dos tests.

Instalação Linux.
```bash
curl -o allure-2.6.0.tgz -Ls https://dl.bintray.com/qameta/generic/io/qameta/allure/allure/2.6.0/allure-2.6.0.tgz   
sudo tar -zxvf allure-2.6.0.tgz -C /opt/   
sudo ln -s /opt/allure-2.6.0/bin/allure /usr/bin/allure  
```

Verificar a Instalação
```bash
allure --version
```

####Executar o Allure
Após a execusão dos tests execute o seguinte comando
```bash
allure serve target/surefire-reports
```



