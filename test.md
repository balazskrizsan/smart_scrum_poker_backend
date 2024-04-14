# Test info

### Test env string
SERVER_PORT=9999;SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:46040/smartscrumpoker;SERVER_SOCKER_FULL_HOST=wss://localhost:9999/ws;SPRING_DATASOURCE_USERNAME=admin;SITE_DOMAIN=not.yet;SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=10;SERVER_SSL_ENABLED=true;SPRING_DATASOURCE_PASSWORD=admin_pass;SITE_FRONTEND_HOST=https://localhost:4200;SERVER_SSL_KEY_STORE_PASSWORD=password;SERVER_ENV=TEST;SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=10;SERVER_SSL_KEY_STORE=classpath:keystore/dev.p12

### Test SQL key setup

| table           | id map       |
|-----------------|--------------|
| poker           | 100xxx       |
| ticket          | 101xxx       |
| insecure_user   | 102xxx       |
| vote            | 103xxx       |
| in_game_players | no increment |
| session keys    | 105xxx       |