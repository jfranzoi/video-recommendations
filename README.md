# Video Recommendations

Sample API for solving the "Video Recommendations" coding problem.

## Tooling

* JDK 17
* maven, via maven wrapper, to be portable
* docker and docker compose, for packaging and deploying
* Spring Boot 3.4.x, as application framework
* PostgresSQL 17.x, as database

Only hard requirement is on JDK version. In order to support a portable setup, a local [.sdkmanrc](./.sdkmanrc) file is provided, 
for [sdkman](https://sdkman.io/). JDK can then be installed and configured by executing:

```shell
sdk env install
```

Please note, feel free to align [.sdkmanrc](./.sdkmanrc). Versions available for your workstation can be retrieved by executing:

```shell
sdk list java
```

## Deployment

Application can be built and started in **development** mode, by executing:

```shell
./mvnw clean package
./mvnw spring-boot:run
```

Same can be achieved in a more **production-ready** mode, by executing:

```shell
./build-and-start.sh
```

Optionally, tests can be skipped, by executing:

```shell
MAVEN_ARGS="-DskipTests" ./build-and-start.sh
```

Once done, application is available at:
* http://localhost:8080/actuator/env

## Notes

See [TODO](TODO.md) to follow development steps. That one, and the commit history, should provide insights on both the 
_incremental_ process, for example by working on individual slices, and _iterative_ process, for example by moving from
application-side filtering (with Java `Predicate`) to persistence-side queries (with JPA `Specification`).

Application is tested at multiple levels:
* **unit** level, eg: individual responsibilities
* **component** level, eg: for data access and for full application acceptance 
  * Data access tests rely on H2 in-memory database, while an attempt is done in order to mimic real PostgreSQL behaviour. Please, see [application-test.properties](src/test/resources/application-test.properties) for details
  * Application acceptance rely on `@SpringBootTest` listening on a local HTTP port, and a `TestRestTemplate` connecting to it

Persistence layer is a relational database (PostgreSQL) for simplicity. Also, movie genres are mapped as simple array items, 
given the low volumes of data, in this exercise.