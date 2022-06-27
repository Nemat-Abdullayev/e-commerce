FROM openjdk:18-alpine
EXPOSE 8080
ADD build/libs/ecommerce.jar ecommerce.jar
ENTRYPOINT ["java","-jar","ecommerce.jar"]