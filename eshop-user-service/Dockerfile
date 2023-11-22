FROM openjdk:17

VOLUME /app/log

ADD /eshop-user-service.jar eshop-user-service.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","eshop-user-service.jar"]
