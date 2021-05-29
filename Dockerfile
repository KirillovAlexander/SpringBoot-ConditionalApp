FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/ConditionalApp-0.0.1-SNAPSHOT.jar devapp.jar
ENTRYPOINT ["java","-jar","/devapp.jar"]