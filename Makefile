.PHONY : compile package test push

compile:
	docker run -it -w="/opt/build" -v ~/.gradle:/.gradle -e GRADLE_USER_HOME=/.gradle -v $$(pwd):/opt/build adoptopenjdk:13-jdk-hotspot ./gradlew assemble installDist

package: compile
	docker build -t access-control -f docker/Dockerfile .


compose: compile
	docker-compose down -v
	docker-compose up --build
