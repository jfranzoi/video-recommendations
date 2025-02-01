# Video Recommendations

Sample API for solving the "Video Recommendations" coding problem.

## Tooling

* Java 17, via [sdkman](https://sdkman.io/), to be portable. See [.sdkmanrc](./.sdkmanrc) file
* maven, via maven wrapper, to be portable
* docker and docker compose, for packaging and deploying
* Spring Boot 3.4.x, as application framework
* PostgresSQL 17.x, as database

## Deployment

Application can be built and started in **development** mode, by executing:

```shell
# only once, to set required JDK version 
sdk env install

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

See [TODO](TODO.md) to follow development steps.

Application is tested at multiple levels:
* unit level, eg: individual responsibilities
* component level, eg: for data access and for full application acceptance 
  * Data access tests rely on H2 in-memory database, while an attempt is done in order to mimic real PostgreSQL behaviour. Please, see [application-test.properties](src/test/resources/application-test.properties) for details
  * Application acceptance rely on `@SpringBootTest` listening on a local HTTP port, and a `TestRestTemplate` connecting to it
