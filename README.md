# 📦 API Cliente

API RESTful desenvolvida com **Spring Boot** para gerenciamento de clientes, com integração à API pública [ViaCEP](https://viacep.com.br/) para busca automática de endereços a partir do CEP.

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Cloud OpenFeign
- Resilience4j (Circuit Breaker)
- MapStruct
- Hibernate / H2
- Lombok
- Bean Validation (Jakarta)

---

## ✅ Boas Práticas Empregadas

### 1. Arquitetura em Camadas
O projeto segue a separação clara de responsabilidades entre as camadas **Controller → Service → Repository**, garantindo baixo acoplamento e alta coesão. O `ClienteController` lida exclusivamente com HTTP, o `ClienteService` contém a lógica de negócio, e o `ClienteRepository` gerencia a persistência.

### 2. DTOs (Data Transfer Objects)
O projeto nunca expõe as entidades diretamente nas respostas da API. São utilizados DTOs específicos para cada operação:
- `ClienteSalvarDTO` — dados obrigatórios para criação de um cliente.
- `ClienteAtualizarDTO` — campos opcionais para atualização parcial (todos os campos são nullable).
- `ClienteDto` — modelo de resposta, com cálculo de idade derivado da data de nascimento.
- `EnderecoDTO` — representação do endereço na resposta.
- `ViaCepDTO` — mapeamento da resposta da API externa ViaCEP.

Essa abordagem protege a integridade do modelo de domínio e evita expor detalhes internos da aplicação.

### 3. Mapeamento com MapStruct
A conversão entre entidades e DTOs é feita de forma declarativa com **MapStruct** através das interfaces `ClienteMapper` e `EnderecoMapper`. O cálculo de idade é realizado diretamente no mapper via expressão Java, mantendo o serviço focado na lógica de negócio.

### 4. Validação de Dados com Bean Validation
As entradas do usuário são validadas declarativamente com anotações como `@NotBlank`, `@NotNull`, `@Past`, `@Size` e `@Pattern`. Isso centraliza as regras de validação nos DTOs, mantendo o código limpo e sem validações manuais espalhadas no serviço ou controller.

### 5. Tratamento Global de Exceções
Um `@RestControllerAdvice` (`GlobalExceptionHandler`) centraliza o tratamento de erros da aplicação, retornando respostas HTTP padronizadas e semânticas:
- `404 Not Found` para recursos inexistentes.
- `400 Bad Request` para CEP inválido ou falhas de validação.
- `503 Service Unavailable` para falhas na integração com o ViaCEP.

Isso elimina blocos try/catch nos controllers e garante respostas consistentes para o consumidor da API.

### 6. Integração com ViaCEP via OpenFeign
A integração com a API externa é feita de forma declarativa com o **Spring Cloud OpenFeign**, mantendo o código limpo e testável. A lógica de conversão e validação do CEP fica isolada no `ViaCepService`, que verifica o campo `erro` retornado pela API antes de prosseguir.

### 7. Resiliência com Circuit Breaker e Retry
- **Circuit Breaker** (Resilience4j): o `ViaCepService` utiliza `@CircuitBreaker` com um `fallbackMethod`, retornando `503 Service Unavailable` quando o serviço de CEP está indisponível.
- **Retry** (Feign): o `FeignConfig` configura uma política de retry com parâmetros externalizados no `application.properties` (`period`, `maxPeriod`, `maxAttempts`), tornando a aplicação mais resiliente a falhas transitórias.

### 8. Controle Transacional
Os métodos de serviço utilizam `@Transactional(readOnly = true)` para operações de leitura, otimizando a performance, e `@Transactional` padrão para operações de escrita, garantindo consistência dos dados.

### 9. Cascade e Relacionamento JPA
O relacionamento `@OneToOne` entre `Cliente` e `Endereco` usa `CascadeType.ALL` e `orphanRemoval = true`, garantindo que o endereço seja persistido, atualizado e removido junto ao cliente automaticamente.

### 10. Dados de Seed Automáticos
O arquivo `data.sql` popula o banco com dados de exemplo na inicialização, facilitando o desenvolvimento e os testes manuais sem necessidade de scripts externos.

### 11. Ordenação da Resposta JSON
`@JsonPropertyOrder` é utilizado nos DTOs de resposta para garantir uma ordem consistente e previsível nos campos JSON, melhorando a legibilidade para quem consome a API.

---

## 🔌 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/cliente` | Lista todos os clientes |
| `GET` | `/cliente/{id}` | Busca cliente por ID |
| `POST` | `/cliente/salvar` | Cria um novo cliente |
| `PATCH` | `/cliente/{id}` | Atualiza parcialmente um cliente existente |
| `DELETE` | `/cliente/{id}` | Remove um cliente |

### Exemplo de Body — POST `/cliente/salvar`

```json
{
  "nome": "Carlos Lima",
  "nascimento": "1998-07-15",
  "cep": "01310-100",
  "numero": "500"
}
```

### Exemplo de Resposta

```json
{
  "id": 1,
  "nome": "Carlos Lima",
  "idade": 26,
  "endereco": {
    "cep": "01310100",
    "uf": "SP",
    "localidade": "São Paulo",
    "bairro": "Bela Vista",
    "logradouro": "Avenida Paulista",
    "numero": "500"
  }
}
```

### Exemplo de Body — PATCH `/cliente/{id}`

Todos os campos são opcionais. Somente os campos enviados serão atualizados:

```json
{
  "nome": "Carlos Lima Atualizado",
  "cep": "20040-020",
  "numero": "100"
}
```

---

## 🚀 Futuras Melhorias

### Segurança
- **Autenticação JWT** com Spring Security para proteger os endpoints, diferenciando perfis como `ADMIN` e `USER`.
- **Rate limiting** para evitar abuso nos endpoints públicos.

### Qualidade e Testes
- **Testes unitários** com JUnit 5 e Mockito cobrindo `ClienteService` e `ViaCepService`.
- **Testes de integração** com `@SpringBootTest` e `MockMvc` para validar os fluxos completos dos controllers.
- **Testes de contrato** para a integração com o ViaCEP usando WireMock.
