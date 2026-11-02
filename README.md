# Sales Report

Serviço Spring Boot para estruturar relatórios de vendas em MongoDB. O projeto usa migrations do Flamingock para inserir e reverter dados iniciais de relatórios.

## Stack

- Java 25
- Spring Boot 4.1.2
- Maven
- MongoDB 8
- Spring Data MongoDB
- Flamingock 1.4.5
- Lombok

## Pré-requisitos

- JDK 25 configurado no `JAVA_HOME` ou na IDE;
- Docker e Docker Compose para executar o MongoDB local;
- Maven Wrapper (`./mvnw`), incluído no projeto.

## Configuração local

A configuração padrão está em `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb://sales-report:sales-report-local@localhost:27017/sales_report?authSource=admin
```

Essas credenciais são somente para desenvolvimento local e correspondem ao `compose.yaml`.

Suba o MongoDB:

```bash
docker compose up -d mongodb
```

Confira o estado do container:

```bash
docker compose ps
```

Se o volume já tiver sido criado com outras credenciais, as variáveis do Compose não alteram o usuário existente. Para recriar um banco local descartável:

```bash
docker compose down -v
docker compose up -d mongodb
```

> `docker compose down -v` remove os dados persistidos do MongoDB.

## Executando

Com Maven:

```bash
./mvnw spring-boot:run
```

No IntelliJ IDEA, execute `SalesReportApplication`. Antes de executar, confirme que a configuração não possui uma variável `MONGODB_URI` ou `SPRING_DATA_MONGODB_URI` sobrescrevendo a propriedade do projeto.

A aplicação inicia na porta `8080` por padrão.

## Migrations

As migrations ficam em `src/main/java/com/example/sales/report/migration` e são descobertas pela anotação `@EnableFlamingock` em `SalesReportApplication`.

A migration de exemplo:

- lê `src/main/resources/migration/report/add-report-q3-retail-b2b-20261101.json`;
- insere o relatório `REPORT_Q3_RETAIL_B2B` se ele ainda não existir;
- usa o target system `mongodb`;
- está marcada como não transacional porque o MongoDB local do Compose é standalone;
- possui rollback reutilizável por nome do relatório.

O JSON de entrada contém apenas parâmetros aceitos por `SalesReportParams`:

```json
{
  "name": "REPORT_Q3_RETAIL_B2B",
  "status": "OPEN",
  "granularity": "WEEK",
  "period": {
    "year": 2026,
    "quarter": 3,
    "startDate": "2026-07-01",
    "endDate": "2026-09-30"
  },
  "criteria": {
    "sellingProgram": "RETAIL",
    "customerRelationship": "B2B"
  },
  "branches": []
}
```

## Testes

Execute todos os testes:

```bash
./mvnw test
```

Execute somente os testes unitários determinísticos:

```bash
./mvnw -Dtest=UtilTests,ProcessMigrationServiceImplTests,MigrationAddReportQ3retailB2BTests test
```

Os testes unitários cobrem parsing JSON, validações e persistência de relatórios, inclusão de branches, rollback e carregamento de recursos da migration. O smoke test de contexto usa `flamingock.management-mode=UNMANAGED` para não executar migrations contra um MongoDB durante a suíte.

## Build e Docker

Gerar o JAR:

```bash
./mvnw clean package
```

Construir a imagem:

```bash
docker build -t sales-report .
```

Executar a imagem, usando um MongoDB acessível pelo container, requer fornecer uma configuração externa adequada para `spring.data.mongodb.uri`.

## Estrutura principal

```text
src/main/java/com/example/sales/report
├── config/       configurações do MongoDB, Jackson e Flamingock
├── dto/          parâmetros de entrada dos relatórios
├── migration/    migrations e serviços de processamento
├── model/        documentos e modelos de domínio
└── repository/   repositórios Spring Data MongoDB

src/main/resources
└── migration/    JSONs usados pelas migrations

src/test/java     testes unitários e smoke test do contexto
compose.yaml      MongoDB local
Dockerfile        build e execução da imagem
```

## Limitações atuais

O projeto ainda não expõe controllers ou endpoints HTTP. O fluxo implementado atualmente é o processamento do relatório inicial pela migration do Flamingock e os serviços/repositórios associados.

Não commite credenciais de ambientes compartilhados ou arquivos locais de configuração.
