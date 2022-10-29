#create new image: docker build -t ImageName .
#run this image on port 8080: docker run -p 8080:8080 ImageName

FROM gradle:7.5.1-jdk18-alpine AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM amazoncorretto:17
EXPOSE 8080
RUN mkdir /spring-boot
COPY --from=build /app/build/libs/*SNAPSHOT.jar /spring-boot/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions",  "-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot/spring-boot-application.jar"]
