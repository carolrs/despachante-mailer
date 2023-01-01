FROM gradle:7.6.0-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:11

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/despachante-mailer-*.jar /app/app.jar

RUN --mount=type=secret,id=MAIL_HOST,dst=/etc/secrets/MAIL_HOST
RUN --mount=type=secret,id=MAIL_PORT,dst=/etc/secrets/MAIL_PORT
RUN --mount=type=secret,id=MAIL_USERNAME,dst=/etc/secrets/MAIL_USERNAME
RUN --mount=type=secret,id=MAIL_PASSWORD,dst=/etc/secrets/MAIL_PASSWORD
RUN --mount=type=secret,id=MAIL_RECIPIENT,dst=/etc/secrets/MAIL_RECIPIENT

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
