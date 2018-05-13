#!/bin/sh

CURRENT_DIR=$(cd `dirname $0` && pwd)
sudo service mongod start
cd ${CURRENT_DIR}/../lockdown-core/target
java -jar lockdown-core-*-SNAPSHOT.jar
trap "cd -" INT
