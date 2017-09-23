#!/bin/bash
#DESC: This script will launch the main PSG process. [1]
. src/main/resources/scripts/setenv.sh
PLATFORM=`uname -p`
if [[ "${PLATFORM}" == "x86_64" ]]; then
	EXTRA_LIB_PATH=/usr/lib/x86_64-linux-gnu:/home/ubuntu/sketchbook/libraries/glvideo/library/linux64
else
	EXTRA_LIB_PATH=/usr/lib//usr/lib/arm-linux-gnueabihf:/home/ubuntu/sketchbook/libraries/glvideo/library/linux-armv6hf
fi
LIB_PATH="src/main/resources/lib:/usr/lib/jni:${EXTRA_LIB_PATH}"
	
export  MVN_OPTS="${MVN_OPTS} -Djava.library.path=\"${LIB_PATH}\""

mvn exec:java -execType=PSG
