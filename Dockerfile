FROM zenika/kotlin:1.3
WORKDIR /app
COPY app /app
ENV AWS_ACCESS_KEY_ID=awskey
ENV AWS_SECRET_ACCESS_KEY=awskey
ENV AWS_DEFAULT_REGION=eu-central-1
RUN bash gradlew assemble
