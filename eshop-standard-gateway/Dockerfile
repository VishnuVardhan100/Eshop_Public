FROM openjdk:17

VOLUME /app/log

ADD /eshop-standard-gateway.jar eshop-standard-gateway.jar

EXPOSE 8765

ENTRYPOINT ["java","-jar","eshop-standard-gateway.jar"]
