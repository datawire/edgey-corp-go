# VeryLargeDataStore

## Build instructions. 
All commands assume that this folder is your working directory.

```
mvn clean install

docker build . -t danielbryantuk/verylargedatastore

docker push danielbryantuk/verylargedatastore
```

## Run locally
All commands assume that this folder is your working directory.
```
./mvnw spring-boot:run
```