FROM openjdk:17
COPY build/libs/nok-server-0.0.1-SNAPSHOT.jar app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "/app.jar", "-Duser.timezone=Asia/Seoul"]