#!/bin/bash
#DESC: This script will install the missing libraries to the local maven repository cache 
. ./setenv.sh
####################################################################################
# ./mvnInstallFile.sh <groupid> <artifactid> <version> file [clasifier] [packaging]
####################################################################################
./mvnInstallFile.sh blobDetection blobDetection 		1.0.0 ../lib/blobDetection.jar
./mvnInstallFile.sh org.gamecontrolplus gamecontrolplus	1.2.1 ../lib/GameControlPlus.jar
./mvnInstallFile.sh g4p g4p-controls					4.1.4	../lib/G4P.jar
./mvnInstallFile.sh gohai.glvideo glvideo				1.2.3	../lib/glvideo.jar
PLATFORM=`uname -p`
if [[ "${PLATFORM}" == "x86_64" ]]; then
	## This will make it work on the 64-bit systems
	ln -vs /usr/lib/jni/libjinput.so ../lib/libjinput-linux64.so
	ln -vs /usr/lib/jni/libgluegen-rt.so ../lib/
else
	ln -vs /usr/lib/jni/libjinput.so ../lib/libjinput-linux.so
fi

