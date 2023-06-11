FROM maven:3.8.7-openjdk-18-slim AS back-build

WORKDIR app
COPY pom.xml /app
# RUN mvn clean install -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
RUN mvn clean install
COPY src /app/src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:18.0.2-jdk-slim
WORKDIR /app
COPY --from=back-build /app/target/*.jar /app/app.jar
ENV TZ=Aisa/Shanghai
RUN ln -snf /usr/shar/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8080
CMD ["sh", "-c", "exec java -jar app.jar"]