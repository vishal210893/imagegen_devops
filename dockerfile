FROM azul/zulu-openjdk:8
VOLUME /tmp
VOLUME /opt/aims
VOLUME /opt/file
WORKDIR /opt/aims
ADD ./target/docker_poc-1.jar docker_poc-1.jar
EXPOSE 8005
ENTRYPOINT exec java -jar /opt/aims/docker_poc-1.jar