# Busca CEP

API Rest para busca de endereço por CEP.

O serviço deverá retornar um endereço com as informações de Rua, Bairro, Cidade e Estado.

Se informado um CEP válido, deve retornar o endereço correspondente.

Se informado um CEP que não exista o endereço, deve substituir um dígito da direita para a esquerda por zero até que o endereço seja localizado (Exemplo: Dado 17526769 tentar com 17526760, 17526700 …).

Se informado um CEP inválido, deve retornar uma mensagem reportando o erro: "CEP inválido".

O serviço recebe e responde no formato JSON.

É necessário estar autenticado para realizar a consulta do endereço.

Ao realizar o login será retornado o token JWT que deverá ser enviado na consulta do CEP. Esse token expira em 30 minutos (configurável).

**Exemplo:**

Login - Requisição
```shell
curl --location --request POST 'http://localhost:8080/api/login' --header 'Content-Type: application/json' \
--data-raw '{
    "usuario" : "admin@teste.com.br",
    "senha" : "Admin@123"
}'
```

Login - Resposta
```shell
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0ZS5jb20uYnIiLCJhdXRoIjp7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSwiaWF0IjoxNjEwMzQ3Mzc2LCJleHAiOjE2MTAzNDkxNzZ9.wPcFvhLArhP-z7cIouJaVCa0L4IsoSqPYZ0xCLI0508"
}
```

Consulta CEP - Requisição
```shell
curl --location --request POST 'http://localhost:8080/api/endereco/' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0ZS5jb20uYnIiLCJhdXRoIjp7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSwiaWF0IjoxNjEwMzQ3Mzc2LCJleHAiOjE2MTAzNDkxNzZ9.wPcFvhLArhP-z7cIouJaVCa0L4IsoSqPYZ0xCLI0508' \
--header 'Content-Type: application/json' \
--data-raw '{"cep" : "01310-100"}'
```

Consulta CEP - Resposta
```shell
{
    "rua": "Avenida Manoel Pereira",
    "bairro": "Jardim Morumbi",
    "cidade": "Marília",
    "estado": "SP"
}
```

## Tecnologias Utilizadas

- Java 11
- IDE Spring STS
- Maven para gerenciar as dependências
- Spring Boot, Starters Web/Test, Data JPA, Security, Actuator (métricas), DevTools, JUnit5, Mockito, Jacoco (relatório de cobertura dos testes), Swagger
- SonarQube plugin para avaliar a qualidade do código em servidor local
- H2 banco de dados em memória para simplificar o teste
- Postman para realizar requisições de teste na api
- Docker
- Git


## Arquitetura

Dividida em 3 camadas

**Persistência**

Interface EnderecoRepository herda de JpaRepository (Spring Data) para realizar operação de pesquisa do endereço no banco de dados.

Interface UsuarioRepository herda de JpaRepository (Spring Data) para pesqquisar, incluir e excluir usuário no banco de dados.

Foi criado a classe modelo Endereco para armazenar as informações de Rua, Bairro, Cidade, Estado e CEP.
Também foi criado a classe modelo Usuario para armazenar as credenciais de acesso.

O mapeamento objeto-relacional é feito com JPA (Hibernate).



**Negócio**

Foi criado a classe EnderecoService para checar a existência do endereço e se necessário pesquisar pelos CEPs alternativos.

Também foi criado a classe UsuarioService para permitir realizar login e incluir/excluir usuário.

**Apresentação**

Classe EnderecoController responsável por expor e receber a requisição. Quando recebe um CEP válido é gerado um Log.

Classe EnderecoDTO para flexibilizar a formatação do retorno da API, independente do modelo do banco de dados.
Foram criadas as classes ControllerExceptionHandler/CustomException/ErrorMsg para retornar mensagem de erro tratada.

A classe LoginController expõe o endpoint para autenticação.

A classe UsuarioController expõe os endpoints para incluir e excluir usuário.

## Testes

A classe EnderecoServiceTest possui métodos para validar os cenários de (CEP válido, inválido, não encontrado) testando a integração da classe EnderecoService com a interface EnderecoRepository.

Foi criado a classe EnderecoControllerTest com objetivo de simular o comportamento do método buscaEnderecoPorCep(cep) para vários cenários também utilizando dados mocados.

A classe EnderecoControllerIntegratioTest contém testes simulando a integração com todas as camadas do serviço e também com o banco de dados H2 criado em memória e já populado com dados fake de CEP/Endereco. Nessa classe foi possível simular o comportamento do serviço desde a entrada do cep até o retorno do Endereço.

Classe LoginControllerTest para realizar testes simulando a autenticação.

Classe UsuarioControllerIntegrationTest para realizar testes de permissão para incluir/excluir usuário.

Foi utilizado a biblioteca Jacoco para geração de relatório de cobertura dos testes.

## Extras
O arquivo "import.sql" é um script SQL para popular o banco de dados com os endereços para teste e também 2 usuários, sendo:

admin@teste.com.br

Admin@123

user@teste.com.br

User@123

Foi disponibilizado um endpoint para extração de métricas: 
http://localhost:8080/actuator

A documentação da API foi feita com Swagger e pode ser acessada em: 
http://localhost:8080/swagger-ui/index.html

Foi criado um arquivo Dockerfile que gera uma imagem para o Docker.