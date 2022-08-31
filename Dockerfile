FROM gradle:7.0.2-jdk11 AS build
COPY . /workdir
WORKDIR /workdir
RUN gradle clean bootJar --no-daemon

FROM openjdk:11.0.11-jre-slim
COPY --from=build /workdir/build/libs/spring-boot-vanilla*.jar /app.jar
CMD ["java", "-jar", "-Dspring.datasource.url=jdbc:postgresql://yugabyte_container:5433/spring_boot_vanilla", "/app.jar"]
