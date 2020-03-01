FROM java:8-jdk-alpine

COPY ./target/fileprocessor-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app/

CMD ["java", "-jar", "/usr/app/fileprocessor-0.0.1-SNAPSHOT.jar"]

