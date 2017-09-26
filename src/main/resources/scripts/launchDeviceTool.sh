#!/bin/bash
#DESC: This script will launch device tool configuration GUI. [1](#01)
EXECDIR=$(dirname "$0")
if [[ "${EXECDIR}" == "." ]]; then
	cd ../../../../	
fi	
. src/main/resources/scripts/setenv.sh
mvn -e exec:exec -DexecType=device
