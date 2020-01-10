FROM openjdk:8-jdk-alpine
EXPOSE 9093
WORKDIR /usr/src/app
#RUN mvn package
ADD /target/customer-service-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]