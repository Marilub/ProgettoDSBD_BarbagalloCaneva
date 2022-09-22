FROM maven:3-jdk-8 as builder
WORKDIR /project
COPY . .
RUN mvn package

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=builder /project/target/ReceptionService-0.0.1-SNAPSHOT.jar ./receptionservice.jar
ENTRYPOINT ["/bin/sh","-c"]
CMD ["java -jar receptionservice.jar"]