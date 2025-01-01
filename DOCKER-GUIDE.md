# Dockerizing Feedify

## Backend File:

```Docker
## slim here caused an enhancement in size of backend
FROM openjdk:23-jdk-slim  

## initialize working directory
WORKDIR /app

## copy the jar from host machine to docker workspace --> /app/feedify-backend.jar
COPY target/backend-0.0.1-SNAPSHOT.jar feedify-backend.jar

## Convention i found to EXPOSE the port
EXPOSE 8080

## Command to execute, NOTE: CMD Purpose: Provides default arguments for the ENTRYPOINT or specifies the command to run when ENTRYPOINT is not set.
CMD ["java", "-jar", "feedify-backend.jar"] 
```

## Frontend File:

```Docker
## Initial base image
FROM node:20-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build


## Final image, its host now is the build image
FROM node:20-alpine
WORKDIR /app
RUN npm install -g serve

## copies build from working directory of build image to working directory of final image
COPY --from=build /app/dist /app/dist

## Expose the port for convention
EXPOSE 5173
CMD ["serve", "-s", "dist", "-l", "5173"]
```


## MULTISTAGE BUILD OPRIMIZATIONS:

| REPOSITORY                  | TAG     | IMAGE ID       | CREATED             | SIZE   |
|-----------------------------|---------|----------------|---------------------|--------|
| feedify-backend-optimized   | latest  | 86cc3559ce04   | 5 seconds ago       | 518MB  |
| feedify-frontend-optimized  | latest  | 0f7943bad66a   | 3 minutes ago       | 150MB  |
| feedify-frontend            | latest  | 385a173a476e   | About an hour ago   | 754MB  |
| feedify-backend             | latest  | 15519c837d88   | About an hour ago   | 647MB  |


## IMPORTANTE NOTE/BLOCKER:
- Redis on Docker compose cannot have the hostnames as localhost, The backend container sees localhost as itself, so it cannot connect to the redis container.
- That's what made buggy issue for reddis connection in Milestone3 discussion.