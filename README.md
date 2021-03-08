#Pojeto sicredi-app

- Link API na Nuvem: https://sicrediapp.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

##Tecnologias
- Java 11
- Spring boot
- Mysql 8
- Open API 3
- Apache Kafka
- Spring Security
- Google Cloud Patform
##Padrões
- Conventional commits
- MVC
- Testes unitários na camada de serviço
- Gitflow
- Documentação através da Open API 3

##Escolhas
- Threads foram usadas para tratar a concorrência de sessões de votações abertas ao mesmo tempo e assim também como encerrar a sessão
- Apache Kafka foi a solução para publicar uma mensagem que uma sessão foi encerrada por se tratar de uma tecnologia altamente usada no mercado.
- Versionamento da API tratado por CI/CD com o Jenkins que facilita a automação de integração e entrega da aplicação. A versão altera conforme o que foi desenvolvido e aprovado pelo time de teste.

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=coverage)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=bugs)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=security_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)

##OBS
- Investigando o porque o SonarCloud não identifca a % da covertura de testes unitários.
- IP do ApacheKafka rodando em uma VM separada no Windows. ALterar configuração de IP conforme o ambiente
- Configuração de conexão do banco de dados já está apontando para o banco de dados na nuvem(GCP)
- É preciso criar um banco de dados "sicrediapp" e alterar a configuração no application.properties caso seja necessário apontar para um banco local.
- Imagem Docker: https://hub.docker.com/repository/docker/fernandocauper/sicrediapp
- Porta padrão da imagem do docker: 8080
