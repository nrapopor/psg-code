#!/bin/bash
#DESC: This script will launch initial settings setup. [1]
. src/main/resources/scripts/setenv.sh
mvn exec:java -execType=settings
