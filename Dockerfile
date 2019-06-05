FROM zenika/kotlin:1.3
WORKDIR /app
COPY app /app
ENV AWS_ACCESS_KEY_ID=awskey
ENV AWS_SECRET_ACCESS_KEY=awskey
RUN bash gradlew assemble
