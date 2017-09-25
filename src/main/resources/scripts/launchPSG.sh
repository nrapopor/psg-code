#!/bin/bash
#DESC: This script will launch the main PSG process. [1](#01)
EXECDIR=$(dirname "$0")
if [[ "${EXECDIR}" == "." ]]; then
	cd ../../../../	
fi	
. src/main/resources/scripts/setenv.sh
##echo LIBDIR="$LIBDIR"
PLATFORM=`uname -p`
if [[ "${PLATFORM}" == "x86_64" ]]; then
	EXTRA_LIB_PATH="/usr/lib/x86_64-linux-gnu:/home/ubuntu/sketchbook/libraries/glvideo/library/linux64"
else
	EXTRA_LIB_PATH="/usr/lib/usr/lib/arm-linux-gnueabihf:/home/ubuntu/sketchbook/libraries/glvideo/library/linux-armv6hf"
fi
LIB_PATH="src/main/resources/lib:/usr/lib/jni:${EXTRA_LIB_PATH}"
	
#-Djogamp.debug.JNILibLoader=true

mvn exec:exec -DexecType=PSG -Dlibrary.path=-Djava.library.path=${LIB_PATH}
