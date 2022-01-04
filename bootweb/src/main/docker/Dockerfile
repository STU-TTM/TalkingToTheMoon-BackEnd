FROM ppppk/ttm:1.0
VOLUME /tmp
ADD boot-web-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]