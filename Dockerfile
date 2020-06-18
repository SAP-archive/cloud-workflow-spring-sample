FROM openjdk:14-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sap-cloud-workflow-spring-sample.jar

ENTRYPOINT ["java","-jar","/sap-cloud-workflow-spring-sample.jar"]