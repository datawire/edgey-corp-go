FROM golang:alpine
LABEL MAINTAINER="Peter ONeill"

WORKDIR /app
COPY main.go .

RUN go build main.go
EXPOSE 3000

ENTRYPOINT ./main