#!/usr/bin/env sh
# Apache Maven Wrapper (minimal)
PRG="$0"
# resolve links - only if required
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/"`expr "$link" : '\(.*\)'`
  fi
done
BASEDIR=`dirname "$PRG"`
if [ -z "$BASEDIR" ]; then
  BASEDIR="."
fi
MVNW_DIR="$BASEDIR/.mvn/wrapper"
MVNW_JAR="$MVNW_DIR/maven-wrapper.jar"
if [ ! -f "$MVNW_JAR" ]; then
  echo "Downloading Maven Wrapper jar..."
  mkdir -p "$MVNW_DIR"
  if command -v curl >/dev/null 2>&1; then
    curl -s -f -L -o "$MVNW_JAR" "https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$MVNW_JAR" "https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"
  else
    echo "curl or wget is required to download the Maven Wrapper jar" >&2
    exit 1
  fi
fi
exec java -Dmaven.multiModuleProjectDirectory="$BASEDIR" -cp "$MVNW_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"


