#!/usr/bin/env bash

echo "Building..."
docker compose -f docker/compose.yml --profile build up

echo "Starting..."
docker compose -f docker/compose.yml --profile deploy up --build --force-recreate --remove-orphans

# See http://localhost:8080/actuator/env