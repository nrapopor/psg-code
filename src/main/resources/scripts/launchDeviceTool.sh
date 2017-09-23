#!/bin/bash
#DESC: This script will launch device tool configuration GUI. [1]
. src/main/resources/scripts/setenv.sh
LIB_PATH="src/main/resources/lib:/usr/lib/jni"
	
export  MVN_OPTS="${MVN_OPTS} -Djava.library.path=\"${LIB_PATH}\""

mvn exec:java -execType=device
