FROM gradle:8.7.0-jdk21-alpine as build

WORKDIR /url-shortener-service

COPY gradle ./gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

COPY src ./src

RUN gradle clean build


FROM amazoncorretto:21.0.2-alpine

WORKDIR /url-shortener-service

COPY --from=build /url-shortener-service/build/libs/url-shortener-service-*.jar .

RUN rm -f url-shortener-service-*-plain.jar

RUN mv url-shortener-service-*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
