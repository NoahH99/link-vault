FROM gradle:8.7.0-jdk21-alpine as build

WORKDIR /backend

COPY gradle ./gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

COPY src ./src

RUN gradle clean build


FROM amazoncorretto:21.0.2-alpine

WORKDIR /backend

COPY --from=build /backend/build/libs/backend-*.jar .

RUN rm -f backend-*-plain.jar

RUN mv backend-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
