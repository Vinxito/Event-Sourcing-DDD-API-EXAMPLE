FROM openjdk:11-slim-buster
WORKDIR /app
COPY /apps/target/*.jar /opt/app.jar
CMD ["java","-jar","/opt/app.jar"]