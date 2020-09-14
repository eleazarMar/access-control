FROM adoptopenjdk/openjdk13:jre-13.0.2_8-alpine

RUN apk add netcat-openbsd
COPY build/install/access-control /opt/application
WORKDIR /opt/application

CMD ["./bin/access-control"]
