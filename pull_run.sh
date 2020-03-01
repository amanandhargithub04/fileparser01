#!/bin/bash
docker stop file-processor-web
docker rm file-processor-web
docker rmi amanandhardocker04/fileprocessor:v1
docker image prune -f
docker pull amanandhardocker04/fileprocessor:v1
docker run --name file-processor-web -d -p 8080:8080 amanandhardocker04/fileprocessor:v1