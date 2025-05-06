FROM eclipse-temurin:17-jdk

WORKDIR /app

# Gradle build 결과물인 JAR 파일 복사
COPY build/libs/*.jar app.jar
COPY src/main/resources/application.yml BOOT-INF/classes/application.yml

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]