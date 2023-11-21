FROM openjdk:17

EXPOSE 8761

ADD /eshop-service-registry.jar eshop-service-registry.jar

ENTRYPOINT ["java","-jar","eshop-service-registry.jar"]

ENV SPRING_SERVER_PORT=$SPRING_SERVER_PORT
ENV SPRING_SERVER_NAME=$SPRING_SERVER_NAME
