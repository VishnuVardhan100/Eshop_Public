FROM openjdk:17

VOLUME /app/log

ADD /eshop-inventory-service.jar eshop-inventory-service.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","eshop-inventory-service.jar"]
