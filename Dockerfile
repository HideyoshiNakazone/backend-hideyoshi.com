#
# Build stage
#
FROM maven:3.6.0-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -Dmaven.test.skip -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jdk

COPY --from=build /home/app/target/*.jar app.jar
COPY src/main/resources/* credentials/
ENTRYPOINT ["java","-jar","/app.jar"]