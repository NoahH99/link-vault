FROM amazoncorretto:21.0.2-alpine

WORKDIR /app

COPY build/libs/backend-0.1.0.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]