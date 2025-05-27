FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -Dfile.encoding=UTF-8

FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# JAR dosyasını kopyala
COPY --from=build /app/target/*.jar app.jar

# Start script
COPY start.sh /start.sh
RUN chmod +x /start.sh

EXPOSE 8082

CMD ["/start.sh"]
