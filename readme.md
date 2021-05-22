# Golden Raspberry Awards

## Objetivos:
- Carga de dados a partir de um arquivo CSV, de filmes indicados e vencedores
  da categoria Pior Filme do Golden Raspberry Awards;
- API RESTFull disponibilizando os produtores que ganharam o prêmio mais de uma vez, mostrando o menor e o maior intervalo entre prêmios consecutivos. 

## Tecnologias utilizadas:
- Java 11
- Spring Boot
- Banco de dados H2

## Testes de Integração:
- Testes de integração disnibilizados na classe `GoldenRaspberryAwardsApplicationTests`;

## Instruções para execução:
- Executar por uma IDE, ou através de servidor de aplicação;
- Para gerar o war, usar o comando `mvn clean package`. O arquivo será disponbilizado em: `Pasta do projeto\target`;
- O arquivo conténdo os dados dos filmes deve ser parametrizado no arquivo `application.properties`, propriedade `app.worstMovies.filePath`. (O valor padrão está configurado como `C:\temp\movielist.csv`);
- Esse arquivo será carregado para o banco de dados ao iniciar a aplicação.
- Para obter os dados retornado da API RESTFull utilizar algumas das possibilidades:
  - Através do link: `http://localhost:8080/worstMovie/producerAwardsInterval`; 
  - Feature de IDE;
  - Software de teste de API RESTFull como por exemplo: Insomnia (https://insomnia.rest/pricing);
