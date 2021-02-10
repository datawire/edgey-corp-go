# VeryLargeJavaService

## Build instructions. 
All commands assume that this folder is your working directory.

```
mvn clean install

docker build . -t peteroneilljr/verylargejavaservice

docker push peteroneilljr/verylargejavaservice
```

## Run locally
All commands assume that this folder is your working directory.
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--nodeservice.host=localhost"
```