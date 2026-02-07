#!/bin/sh
# Maven Wrapper
BASE_DIR="$(cd "$(dirname "$0")"; pwd -P)"
MAVEN_PROJECTBASEDIR="$BASE_DIR"
MAVEN_WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
JAVA_EXE="java"
if [ -n "$JAVA_HOME" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
fi
exec "$JAVA_EXE" -cp "$MAVEN_WRAPPER_JAR" -Dmaven.home="$MAVEN_PROJECTBASEDIR" -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" org.apache.maven.wrapper.MavenWrapperMain "$@"
