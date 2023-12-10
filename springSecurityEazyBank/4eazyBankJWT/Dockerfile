#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER eazybytes.com

EXPOSE 8080

# Add the application's jar to the container
COPY target/eazyBankJWT-1.0-SNAPSHOT.jar eazyBankJWT-1.0-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/eazyBankJWT-1.0-SNAPSHOT.jar"]

#docker run -p 8080:8080 --restart always justamitsaha/eazy-bank-api-jwt-aws