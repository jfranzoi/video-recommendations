#!/usr/bin/env bash

./mvnw clean package -DskipTests

docker compose -f docker/compose.yml up --build

# See http://localhost:8080/actuator/env