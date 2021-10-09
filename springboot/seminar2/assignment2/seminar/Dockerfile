FROM openjdk:11-jdk-slim
WORKDIR /app
COPY . /app
RUN ./gradlew bootJar
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod build/libs/draft-0.0.1.jar
