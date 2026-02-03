# ======================
# BUILD STAGE
# ======================
# start
FROM maven:3.9.8-amazoncorretto-21 AS build

# copy source code and pom.xml to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# build source code with maven
RUN mvn -B clean package -DskipTests

# ======================
# RUNTIME STAGE
# ======================
FROM amazoncorretto:21.0.4

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]