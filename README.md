#Pojeto sicredi-app

##Tecnologias
- Java 11
- Spring boot
- Mysql 8
- Open API 3
- Apache Kafka
- Spring Security
##Padrões
- Conventional commits
- MVC
- Testes unitários na camada de serviço
- Gitflow
- Documentação através da Open API 3

##Escolhas
- Threads foram usadas para tratar a concorrência de sessões de votações abertas ao mesmo tempo e assim também como encerrar a sessão
- Apache Kafka foi a solução para publicar uma mensagem que uma sessão foi encerrada por se tratar de uma tecnologia altamente usada no mercado.

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=coverage)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=bugs)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Thilin_sicredi-app&metric=security_rating)](https://sonarcloud.io/dashboard?id=Thilin_sicredi-app)