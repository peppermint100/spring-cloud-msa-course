FROM openjdk:17-ea-11-slim
VOLUME /tmp
COPY target/ecommerce-1.0.jar DiscoveryService.jar
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]