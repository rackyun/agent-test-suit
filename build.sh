#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true -DfailIfNoTests=false
# download skywalking-agent
# run test
mvn test