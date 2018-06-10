CURRENT_DIR=$(cd `dirname $0` && pwd)
cd ${CURRENT_DIR} &&
	./build.sh && 
	./run.sh
cd -
