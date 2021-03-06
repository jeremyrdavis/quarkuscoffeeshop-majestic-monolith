#!/bin/bash

docker run -i -rm --network=coffeeshop-network \
  -p 8080:8080 \
  -e CORS_ORIGINS=${CORS_ORIGINS} \
  -e STREAM_URL=${STREAM_URL} \
  -e STORE_ID=${STORE_ID} \
  -e PGSQL_URL=${PGSQL_URL} \
  -e PGSQL_USER=${PGSQL_USER} \
  -e PGSQL_PASSWORD=${PGSQL_PASSWORD} \
  -e PGSQL_URL_KITCHEN=${PGSQL_URL_KITCHEN} \
  -e PGSQL_USER_KITCHEN=${PGSQL_USER_KITCHEN} \
  -e PGSQL_PASSWORD_KITCHEN=${PGSQL_PASSWORD_KITCHEN} \
  -e PGSQL_URL_BARISTA=${PGSQL_URL_BARISTA} \
  -e PGSQL_USER_BARISTA=${PGSQL_USER_BARISTA} \
  -e PGSQL_PASSWORD_BARISTA=${PGSQL_PASSWORD_BARISTA} \
  jeremydavis/quarkuscoffeeshop-majestic-monolith:latest
