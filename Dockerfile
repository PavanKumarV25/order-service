FROM openjdk:11 as build

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} orderservice.jar

ENTRYPOINT ["java","-jar","/orderservice.jar"]

EXPOSE 8813