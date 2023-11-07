tag?=golang

.Phony: build
build: 
	go build main.go

.Phony: build-image
build-image:
	docker build -t datawire/dataprocessingservice:$(tag) .

.Phony: push-image
push-image: build-image
	docker push datawire/dataprocessingservice:$(tag)