FROM gradle:jdk11-alpine AS build
COPY src /home/gradle/src
COPY build.gradle settings.gradle  /home/gradle/
WORKDIR /home/gradle/
RUN gradle clean build bootJar

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY --from=build "home/gradle/build/libs/demo-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 9091
ENTRYPOINT ["java","-jar","app.jar"]