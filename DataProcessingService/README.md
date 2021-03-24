# DataProcessingService

## Build instructions. 
All commands assume that this folder is your working directory.

```
docker build . -t datawire/dataprocessingservice:golang

docker push datawire/dataprocessingservice:golang
```

## Run locally
All commands assume that this folder is your working directory.
```
go run main.go -c=blue -env local -d verylargedatastore:8080 -p 3000
```