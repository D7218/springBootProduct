@echo off
rem Apache Maven Wrapper (minimal)
setlocal
set MVNW_CALL="%~f0"
set BASEDIR=%~dp0
if "%BASEDIR%"=="" set BASEDIR=.
set MVNW_DIR=%BASEDIR%.mvn\wrapper
set MVNW_JAR=%MVNW_DIR%\maven-wrapper.jar
if not exist "%MVNW_JAR%" (
  echo Downloading Maven Wrapper jar...
  mkdir "%MVNW_DIR%" 2>nul
  powershell -Command "try{ (New-Object System.Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar','%MVNW_JAR%') } catch { exit 1 }"
  if errorlevel 1 (
    echo Failed to download maven-wrapper.jar. Please ensure Internet access.
    exit /b 1
  )
)
java -Dmaven.multiModuleProjectDirectory="%BASEDIR%" -cp "%MVNW_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal


