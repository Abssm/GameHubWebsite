#!/bin/sh
# Maven Wrapper script for Windows
# This downloads Maven if not present

MAVEN_VERSION="3.9.5"
MAVEN_HOME="${HOME}/.maven/apache-maven-${MAVEN_VERSION}"
MAVEN_JAR="${HOME}/.maven/maven-${MAVEN_VERSION}.jar"

# For Windows
if [ "$OS" = "Windows_NT" ]; then
    JAVA_HOME="C:\Program Files\Java\jdk-17"
    JAVA_CMD="${JAVA_HOME}\bin\java.exe"
else
    JAVA_CMD="java"
fi

# Download Maven if needed
if [ ! -d "$MAVEN_HOME" ]; then
    mkdir -p "${HOME}/.maven"
    curl -fsSL "https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip" -o "$MAVEN_JAR"
fi

"$JAVA_CMD" -cp "$MAVEN_JAR" org.apache.maven.cli.MavenCli "$@"
