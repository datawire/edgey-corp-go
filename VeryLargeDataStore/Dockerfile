FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/VeryLargeDataStore-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]