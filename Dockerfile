## Step 1 - Build
FROM maven:3.6.0 as builder
VOLUME /tmp
RUN mkdir -p /usr/src/app/
WORKDIR /usr/src/app/
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /usr/src/app/src/
RUN mvn package
## Step 2 - Push
FROM openjdk:11
COPY --from=builder /usr/src/app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar", "/app/app.jar"]
#ENTRYPOINT ["java","-jar", "-DDATABASE.HOST=${DATABASE_HOST}", " -DDATABASE.USER=${DATABASE_USER}", "-DDATABASE.PASSWORD=${DATABASE_PASSWORD}", "-DDATABASE.NAME=${DATABASE_NAME}", " -DDATABASE.PORT=${DATABASE_PORT}", "/app/app.jar"]