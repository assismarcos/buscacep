FROM adoptopenjdk:11-jre-hotspot

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY ./target/buscacep-0.0.1-SNAPSHOT.jar buscacep.jar

CMD ["java","-jar","buscacep.jar"]