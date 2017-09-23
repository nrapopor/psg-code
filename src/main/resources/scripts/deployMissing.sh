#!/bin/bash
#DESC: This script will deploy the missing libraries to the remote maven repository server
. ./setenv.sh
####################################################################################
# ./mvnDeployFile.sh <groupid> <artifactid> <version> file [clasifier] [packaging]
####################################################################################
#./mvnDeployFile.sh blobDetection blobDetection 		1.0.0 ../lib/blobDetection.jar
#./mvnDeployFile.sh org.gamecontrolplus gamecontrolplus	1.2.1 ../lib/GameControlPlus.jar
#./mvnDeployFile.sh g4p g4p-controls					4.1.4	../lib/G4P.jar
#./mvnDeployFile.sh gohai.glvideo glvideo				1.2.3	../lib/glvideo.jar

