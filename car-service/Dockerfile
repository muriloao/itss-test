FROM openjdk:8-jdk-alpine
EXPOSE 9094
WORKDIR /usr/src/app
#RUN mvn package
ADD /target/credit-service-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]