FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar test-task.jar
ENTRYPOINT ["java","-jar","/test-task.jar"]