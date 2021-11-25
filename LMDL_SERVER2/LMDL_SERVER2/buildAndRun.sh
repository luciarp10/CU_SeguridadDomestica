#!/bin/sh
mvn clean package && docker build -t com.mycompany/LMDL_SERVER2 .
docker rm -f LMDL_SERVER2 || true && docker run -d -p 9080:9080 -p 9443:9443 --name LMDL_SERVER2 com.mycompany/LMDL_SERVER2