FROM openjdk:17

VOLUME /app/log

ADD /eshop-service-registry.jar eshop-service-registry.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","eshop-service-registry.jar"]
