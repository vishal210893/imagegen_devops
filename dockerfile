FROM azul/zulu-openjdk:17

# Update package lists and install curl
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

VOLUME /tmp
VOLUME /opt/aims
VOLUME /opt/file
# COPY ALL THINGS INSIDE NGINX FOLDER TO TMP FOLDER
COPY /nginx /tmp
WORKDIR /opt/aims
ADD ./target/dockerpoc-1.jar dockerpoc-1.jar
EXPOSE 8005
ENTRYPOINT exec java -jar /opt/aims/dockerpoc-1.jar