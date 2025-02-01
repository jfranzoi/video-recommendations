# Video Recommendations

Sample API for solving the "Video Recommendations" coding problem.

## Tooling

* Java 17, via [sdkman](https://sdkman.io/), to be portable. See [.sdkmanrc](./.sdkmanrc) file
* maven, via maven wrapper, to be portable
* Spring Boot 3.4.x
* docker and docker compose

## Build & Start

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

Once done, application is available at:
* http://localhost:8080/actuator/env