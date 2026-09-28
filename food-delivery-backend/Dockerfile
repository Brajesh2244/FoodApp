FROM eclipse-temurin:17-jre
WORKDIR /app
COPY app.jar app.jar
ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -Xmx384m -Dserver.port=${PORT:-8081} -jar app.jar"]
