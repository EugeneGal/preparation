FROM amazoncorretto:17-alpine3.17

# install maven during image creating
RUN apk add --no-cache maven

# make /app workdir in image (further in container)
WORKDIR /app

# copy files to /app in image
COPY . .

# build project during image creating
RUN mvn clean install

# run service while starting container
ENTRYPOINT ["mvn", "spring-boot:run"]
