#!/bin/sh

CURRENT_DIR=$(cd `dirname $0` && pwd)
cd ${CURRENT_DIR}/../lockdown-rest/target
java -jar lockdown-rest-*-SNAPSHOT.jar
trap "cd -" INT
