#!/bin/bash
. ./setenv.sh
####################################################################################
# ./mvnInstallFile.sh <groupid> <artifactid> <version> file [clasifier] [packaging]
####################################################################################
./mvnInstallFile.sh guicomponents guicomponents			2.0.1 ../lib/guicomponents.jar
./mvnInstallFile.sh blobDetection blobDetection 		1.0.0 ../lib/blobDetection.jar
./mvnInstallFile.sh org.gamecontrolplus gamecontrolplus	1.2.1 ../lib/GameControlPlus.jar
./mvnInstallFile.sh JMyron JMyron                    	0.0.26 ../lib/JMyron.jar
