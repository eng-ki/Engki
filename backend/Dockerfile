# production stage

FROM openjdk:11-jre-slim

COPY ./build/libs/engki-0.0.1.jar .

EXPOSE 8889
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "engki-0.0.1.jar"]
