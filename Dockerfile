FROM openjdk:8-jdk-alpine

EXPOSE 8080

ARG JAR_FILE=target/order-api-*.jar

WORKDIR /opt/app

COPY ${JAR_FILE} order-api.jar

ENTRYPOINT ["java","-jar","order-api.jar"]