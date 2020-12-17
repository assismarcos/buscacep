# Busca CEP

API Rest para busca de endereço por CEP.

O serviço deverá retornar um endereço com as informações de Rua, Bairro, Cidade e Estado.

Se informado um CEP válido, deve retornar o endereço correspondente.

Se informado um CEP que não exista o endereço, deve substituir um dígito da direita para a esquerda por zero até que o endereço seja localizado (Exemplo: Dado 17526769 tentar com 17526760, 17526700 …).

Se informado um CEP inválido, deve retornar uma mensagem reportando o erro: "CEP inválido".

O serviço recebe e responde no formato JSON.

**Exemplo:**

Requisição
```shell
curl -X POST "http://localhost:8080/api/endereco" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"cep\": \"01310-100\"}"
```

Resposta
```shell
{
  "rua": "Avenida Paulista - de 612 a 1510 - lado par",
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

## Tecnologias Utilizadas

- Java 11
- IDE Spring STS
- Maven para gerenciar as dependências
- Spring Boot, Starters Web/Test, Data JPA, DevTools, JUnit5, Mockito, Swagger
- SonarQube plugin para avaliar a qualidade do código em servidor local
- H2 banco de dados em memória para simplificar o teste
- Postman para realizar requisições de teste na api
- Docker
- Git


## Arquitetura

Dividida em 3 camadas

**Persistência**

Foi criado a interface EnderecoRepository herdando de JpaRepository (Spring Data) para realizar operação de pesquisa no banco H2.
Também foi criado a classe modelo Endereco para armazenar as informações de Rua, Bairro, Cidade, Estado e CEP. O mapeamento objeto-relacional é feito com JPA (Hibernate).


**Negócio**

Foi criado a classe EnderecoService para checar a existência do endereço e se necessário pesquisar pelos CEPs alternativos.

**Apresentação**
Foi criado a classe EnderecoController sendo esse o responsável por expor e receber a requisição. Quando recebe um CEP válido é gerado um Log.
Foi criado a classe EnderecoDTO para flexibilizar a formatação do retorno da API, independente do modelo do banco de dados.
Foi criado a classe ControllerExceptionHandler para retornar mensagem de erro tratada.


## Testes

A classe EnderecoServiceTest possui métodos para validar os cenários de (CEP válido, inválido, não encontrado) testando a integração da classe EnderecoService com a interface EnderecoRepository.

Foi criado a classe EnderecoControllerTest com objetivo de simular o comportamento do método buscaEnderecoPorCep(cep) para vários cenários também utilizando dados mocados.

A classe EnderecoControllerIntegratioTest contém testes simulando a integração com todas as camadas do serviço e também com o banco de dados H2 criado em memória e já populado com dados fake de CEP/Endereco. Nessa classe foi possível simular o comportamento do serviço desde a entrada do cep até o retorno do Endereço.


## Extras
O arquivo "import.sql" é um script SQL para popular o banco de dados com os endereços para teste.

A documentação da API foi feita com Swagger e pode ser acessada em: 
http://localhost:8080/swagger-ui/index.html

Foi criado um arquivo Dockerfile que gera uma imagem para o Docker.