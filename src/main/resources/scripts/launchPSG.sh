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
	EXTRA_LIB_PATH="/home/ubuntu/sketchbook/libraries/glvideo/library/linux-armv6hf"
fi
LIB_PATH="${EXTRA_LIB_PATH}:src/main/resources/lib"
	
#-Djogamp.debug.JNILibLoader=true
echo LIB_PATH=${LIB_PATH}
mvn -e exec:exec -DexecType=PSG -DsysType=${PLATFORM} -Dlibrary.path=-Djava.library.path=${LIB_PATH}
