# Stage 1: Build the jar file using Gradle
FROM gradle:7.6-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble --no-daemon

# Stage 2: Run the jar file
ARG MYSQL_HOST
ARG MYSQL_SCHEMA
ARG MYSQL_USER
ARG MYSQL_PASSWORD
ARG JWT_SECRET

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
EXPOSE 8080

# TODO: Use docker secrets
# Using the ARG-ENV workaround to pass ARGs to CMD
ARG MYSQL_SCHEMA
ENV MYSQL_SCHEMA=$MYSQL_SCHEMA
ARG MYSQL_HOST
ENV MYSQL_HOST=$MYSQL_HOST
ARG MYSQL_USER
ENV MYSQL_USER=$MYSQL_USER
ARG MYSQL_PASSWORD
ENV MYSQL_PASSWORD=$MYSQL_PASSWORD
ARG JWT_SECRET
ENV JWT_SECRET=$JWT_SECRET

CMD java -DMYSQL_HOST=$MYSQL_HOST -DMYSQL_SCHEMA=$MYSQL_SCHEMA -DMYSQL_USER=$MYSQL_USER -DMYSQL_PASSWORD=$MYSQL_PASSWORD -DJWT_SECRET=$JWT_SECRET -jar /app/spring-boot-application.jar