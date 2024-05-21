FROM gradle:jdk21-alpine as build

ENV SERVER_ENV=DEV
ENV SERVER_PORT=9999
ENV SERVER_SOCKER_FULL_HOST=wss://localhost:9999/ws
ENV SERVER_SSL_ENABLED=true
ENV SERVER_SSL_KEY_STORE=classpath:keystore/dev.p12
ENV SERVER_SSL_KEY_STORE_PASSWORD=password
ENV SITE_DOMAIN=not.yet
ENV SITE_FRONTEND_HOST=https://localhost:4200
ENV SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=10
ENV SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=10
ENV SPRING_DATASOURCE_PASSWORD=admin_pass
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:46030/smartscrumpoker
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SOCKET_IS_ENABLED_SOCKET_CONNECT_AND_DISCONNECT_LISTENERS=true

WORKDIR /app

COPY . .

RUN gradle build -x test