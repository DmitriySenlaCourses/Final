#!/bin/bash

cd ..
./gradlew build

cd docker

docker-compose build
docker-compose up

