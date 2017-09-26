#!/bin/bash
#DESC: This script will install the missing libraries to the local maven repository cache 
. ./setenv.sh
####################################################################################
# ./mvnInstallFile.sh <groupid> <artifactid> <version> file [clasifier] [packaging]
####################################################################################
./mvnInstallFile.sh blobDetection blobDetection 		1.0.0 ../lib/blobDetection.jar
./mvnInstallFile.sh org.gamecontrolplus gamecontrolplus	1.2.1 ../lib/GameControlPlus.jar
./mvnInstallFile.sh g4p g4p-controls					4.1.4	../lib/G4P.jar
PLATFORM=`uname -p`
if [[ "${PLATFORM}" == "x86_64" ]]; then
	## This will make it work on the 64-bit systems
	ln -vs /usr/lib/jni/libjinput.so ../lib/libjinput-linux64.so
	##ln -vs /usr/lib/jni/libgluegen2-rt.so ../lib/
else
	./mvnInstallFile.sh gohai.glvideo 					glvideo		 1.2.3  /home/ubuntu/sketchbook/libraries/glvideo/library/glvideo.jar
	./mvnInstallFile.sh org.jogamp.jogl.processing 		jogl-all     2.3.2 	/usr/local/lib/processing/core/library/jogl-all.jar
	./mvnInstallFile.sh org.jogamp.jogl.processing 		jogl-all     2.3.2  /usr/local/lib/processing/core/library/jogl-all-natives-linux-armv6hf.jar natives-linux-armv6hf
	./mvnInstallFile.sh org.jogamp.gluegen.processing 	gluegen-rt   2.3.2  /usr/local/lib/processing/core/library/gluegen-rt.jar
	./mvnInstallFile.sh org.jogamp.gluegen.processing 	gluegen-rt   2.3.2  /usr/local/lib/processing/core/library/gluegen-rt-natives-linux-armv6hf.jar natives-linux-armv6hf
	if [ ! -d /home/ubuntu/.m2/repository/gohai/glvideo/glvideo/1.2.3/ ]; then
		mkdir -p /home/ubuntu/.m2/repository/gohai/glvideo/glvideo/1.2.3/
	fi
	if [ ! -L /home/ubuntu/.m2/repository/gohai/glvideo/glvideo/1.2.3/linux-armv6hf ]; then
		ln -s /home/ubuntu/sketchbook/libraries/glvideo/library/linux-armv6hf/ /home/ubuntu/.m2/repository/gohai/glvideo/glvideo/1.2.3/
	fi
	ln -vs /usr/lib/jni/libjinput.so ../lib/libjinput-linux.so
fi

