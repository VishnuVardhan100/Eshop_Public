FROM openjdk:17

VOLUME /app/log

ADD /eshop-communications-service.jar eshop-communications-service.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","eshop-communications-service.jar"]
