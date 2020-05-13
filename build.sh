#!/usr/bin/env bash

mvn clean package -DskipTests=true -DfailIfNoTests=false
# download skywalking-agent
# run test
mvn test