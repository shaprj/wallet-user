FROM jgeraldolima/gradle-7.2.0-jdk11-node-14 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:11
RUN mkdir /opt/app
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /opt/app
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/opt/app/user-0.0.1-SNAPSHOT.jar"]