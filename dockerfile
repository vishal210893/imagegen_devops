FROM azul/zulu-openjdk:8
VOLUME /tmp
VOLUME /opt/aims
WORKDIR /opt/aims
ADD ./target/image_generation-0.0.1.jar imagepoc.jar
EXPOSE 8005
ENTRYPOINT exec java -jar /opt/aims/imagepoc.jar