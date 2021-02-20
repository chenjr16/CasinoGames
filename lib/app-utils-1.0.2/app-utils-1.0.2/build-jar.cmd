@echo off

javac -d classes src\com\apps\util\*.java src\com\apps\util\client\*.java

jar --create --file lib\app-utils-1.0.2.jar -C classes .