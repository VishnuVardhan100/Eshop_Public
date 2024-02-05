FROM openjdk:17

VOLUME /app/log

ADD /eshop-order-service.jar eshop-order-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","eshop-order-service.jar"]
