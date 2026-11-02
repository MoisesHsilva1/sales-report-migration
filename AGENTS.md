# Repository Guidelines

## Project Structure & Module Organization

This is a Spring Boot service using Java 25, Maven, MongoDB, and Flamingock migrations. Application code lives under `src/main/java/com/example/sales/report`, organized into configuration, DTOs, models, repositories, and migration packages. Migration JSON files live under `src/main/resources/migration`. Tests mirror the package layout under `src/test/java`. `compose.yaml` starts the local MongoDB dependency, while `Dockerfile` builds and runs the application image.

## Build, Test, and Development Commands

- `./mvnw test` — compile the project and run the test suite.
- `./mvnw -Dtest=UtilTests,ProcessMigrationServiceImplTests,MigrationAddReportQ3retailB2BTests test` — run the deterministic unit tests without the Spring/MongoDB context.
- `./mvnw spring-boot:run` — run the service locally on port 8080.
- `docker compose up -d mongodb` — start the MongoDB dependency with the repository defaults.
- `./mvnw clean package` — create the deployable JAR in `target/`.
- `docker build -t sales-report .` — build the production container image.

The full suite includes `SalesReportApplicationTests`, a Spring context smoke test configured with `flamingock.management-mode=UNMANAGED`; it validates application startup without executing migrations against MongoDB. Start the dependency with Docker for manual runs that exercise repositories or Flamingock migrations.

The checked-in `application.properties` uses the local development MongoDB credentials from `compose.yaml` so the IntelliJ Spring Boot run configuration works without extra variables. For shared environments, override the connection using an external Spring configuration or environment-specific profile; do not commit production credentials or local environment files.

## Coding Style & Naming Conventions

Use standard Java package naming (lowercase), `PascalCase` for classes, and `camelCase` for methods and fields. Keep DTOs, models, repositories, and migration services in their existing package boundaries. Preserve the existing Spring Boot annotation and constructor-injection patterns. Migration classes use the established sequence and descriptive format, such as `_0001__MigrationAddReportQ3retailB2B`; migration IDs must remain unique and rollback methods should be considered for every schema/data change. Classpath resources must be referenced relative to `src/main/resources` (for example, `migration/report/file.json`), not with the source-tree prefix. No repository formatter or linter is configured, so keep imports clean and match surrounding indentation.

## Testing Guidelines

Tests use JUnit 5, AssertJ, Mockito, and Spring Boot’s test support. Name test classes with the `*Tests` suffix and test methods after the behavior they verify. Prefer deterministic unit tests with mocked repositories/services for application logic; reserve `@SpringBootTest` for context smoke/integration checks. Run focused unit tests first, then `./mvnw test` before submitting changes. Add focused tests for new services, converters, repositories, utilities, and migration behavior. The current suite includes unit tests for `Util`, `ProcessMigrationServiceImpl`, and the report migration, plus an application context smoke test.

## Commit & Pull Request Guidelines

Use Conventional Commits, for example `feat: add channel report` or `fix(migration): correct report key`. Keep commits focused. Pull requests should explain the behavior change, list validation commands, link a relevant issue when available, and call out MongoDB/configuration or migration impacts. Include request/response examples or screenshots when an API or user-visible behavior changes.

## Security & Configuration Tips

Local MongoDB defaults are intended for development only. Review connection strings, credentials, and migration effects before deployment, and use environment-provided secrets in shared environments.
