FROM openjdk:23-jdk
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar feedify-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "feedify-backend.jar"]  
# This is the command that will be run when the container starts, at the ENTRYPOINT
# ENTRYPOINT ["java", "-jar", "/feedify-backend.jar"]

# To build:
# docker build -t feedify-backend -f Dockerfile-backend .
