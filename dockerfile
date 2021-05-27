FROM azul/zulu-openjdk:8
VOLUME /tmp
VOLUME /opt/aims
VOLUME /opt/file
# COPY ALL THINGS INSIDE NGINX FOLDER TO TMP FOLDER
COPY /nginx /tmp
WORKDIR /opt/aims
ADD ./target/dockerpoc-1.jar dockerpoc-1.jar
EXPOSE 8005
ENTRYPOINT exec java -jar /opt/aims/dockerpoc-1.jar