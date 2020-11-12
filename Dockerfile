# 1st layer retrieves Mastr version from config.hjson and compiles jar using Maven
FROM maven:3.6-adoptopenjdk-11 AS MAVEN_BUILD
USER root
COPY pom.xml /usr/src/mastr/
COPY src /usr/src/mastr/src/
# Copy the config ONLY for the use of getting the current version. While the configuration file is mounted at run-time, it will NOT be mounted for building. If you edit the version of the
# program, the image needs to be rebuilt, which makes sense because this likely indicates a change in the code.
COPY conf/config.hjson /usr/src/mastr/
WORKDIR /usr/src/mastr/
# Get version from configuration file using Regex (MUST match format d.d.d-c
RUN grep -oP '(?:"version"\s*:\s*")(.*?)(?:")' ./config.hjson | grep -oP '(\d*\.\d*.\d*-[a-zA-Z])' > version.txt
# Set version to environment variable and package (Maven needs this environment variable)
RUN export MASTR_VERSION && MASTR_VERSION=$(cat version.txt) && mvn package

# 2nd layer runs bot on JRE using Alpine
FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
LABEL maintainer="Gabriel Keller"
COPY --from=MAVEN_BUILD /usr/src/mastr/target/MastrV2-with-dependencies.jar /usr/src/mastr/MastrV2.jar
RUN adduser -D -g '' mastr
RUN chown -R mastr /usr/src/mastr/
USER mastr
ENTRYPOINT ["java", "-jar", "/usr/src/mastr/MastrV2.jar", "usr/src/mastr/conf/config.hjson"]