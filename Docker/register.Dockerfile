FROM eclipse-temurin:17-jdk-alpine

ENV VERSION 1.0.0

WORKDIR /app/RegisterService/

ADD RegisterService-$VERSION.jar $VERSION.jar

EXPOSE 8011

ENTRYPOINT ["java","-jar","1.0.0.jar", "--server"]