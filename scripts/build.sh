#!/bin/sh

CURRENT_DIR=$(cd `dirname $0` && pwd)
cd ${CURRENT_DIR}/..
mvn clean install
RESULTS=$?
cd -

return $RESULTS
