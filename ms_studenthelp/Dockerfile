#run ./gradlew build before docker build
FROM amazoncorretto:17
VOLUME /tmp
ADD build/libs/ms_studenthelp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]


