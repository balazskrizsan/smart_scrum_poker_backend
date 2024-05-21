FROM gradle:jdk21-alpine as build

COPY ./../../SmartScrumPokerBackend-latest.jar /project/

EXPOSE 8181

ENTRYPOINT ["java", "--enable-preview", "-jar", "/project/SmartScrumPokerBackend-latest.jar"]
