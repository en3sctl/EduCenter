@echo off
setlocal
set "MAVEN_BIN=C:\Users\enes\tools\apache-maven-3.9.9\bin"
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.18.8-hotspot"
set "PATH=%MAVEN_BIN%;%JAVA_HOME%\bin;%PATH%"
call mvn exec:java %*
endlocal
