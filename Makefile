
all: build

up:
	@docker-compose up -d

build:
	@mvn clean install -U

run-tests:
	@mvn clean test

test:
	@mvn test

run:
	@mvn spring-boot:run
