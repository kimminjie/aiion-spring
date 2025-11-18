# Build stage
FROM eclipse-temurin:21-jdk-ubi9-minimal AS build
WORKDIR /app

# Install Gradle and required tools
RUN microdnf install -y wget unzip findutils && \
    wget -q https://services.gradle.org/distributions/gradle-8.5-bin.zip && \
    unzip -q gradle-8.5-bin.zip && rm gradle-8.5-bin.zip && \
    microdnf clean all
ENV PATH="/app/gradle-8.5/bin:${PATH}"

# Copy project files
COPY settings.gradle build.gradle ./
COPY server ./server
COPY service ./service

# Build
RUN gradle :server:eurekaServer:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-ubi9-minimal
VOLUME /tmp
COPY --from=build /app/server/eurekaServer/build/libs/*.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
