tag?=latest

.Phony: run
run: 
	./mvnw spring-boot:run -Dspring-boot.run.arguments="--nodeservice.host=localhost"

.Phony: build-image
build-image:
	mvn clean install
	docker build -t datawire/verylargejavaservice:$(tag) .

.Phony: push-image
push-image: build-image
	docker push datawire/verylargejavaservice:$(tag)