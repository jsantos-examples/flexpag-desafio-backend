FROM openjdk

WORKDIR /app

COPY target/payment-scheduler-1.0.0.jar /app/spring-app.jar

ENTRYPOINT ["java","-jar","spring-app.jar"]