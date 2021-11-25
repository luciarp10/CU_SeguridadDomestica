@echo off
call mvn clean package
call docker build -t com.mycompany/LMDL_SERVER2 .
call docker rm -f LMDL_SERVER2
call docker run -d -p 9080:9080 -p 9443:9443 --name LMDL_SERVER2 com.mycompany/LMDL_SERVER2