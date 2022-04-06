dev-start:
	docker-compose -f ./docker/docker-compose.yml  up -d 

dev-stop:
	docker-compose -f ./docker/docker-compose.yml  down 

stack-start:
	docker-compose -f ./docker/docker-compose-full.yml  up -d --force-recreate --build

stack-stop:
	docker-compose -f ./docker/docker-compose-full.yml  down 

build-backend-jvm-based-spring-boot:
	cd backend/jvm-based/spring-boot-java-app;\
	mvn clean install

run-api-jvm-based-spring-boot:
	cd backend/jvm-based/spring-boot-java-app;\
	mvn spring-boot:run

.PHONY: dev-start dev-stop stack-start stack-stop build-backend-jvm-based-spring-boot run-api-jvm-based-spring-boot