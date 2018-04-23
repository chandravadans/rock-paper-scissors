#!/bin/bash

echo "Cleaning up from previous runs"
./mvnw clean

echo "Compiling the project"
./mvnw compile

echo "Running the project"
./mvnw exec:java
