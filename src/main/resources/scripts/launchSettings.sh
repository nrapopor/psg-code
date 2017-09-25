#!/bin/bash
#DESC: This script will launch initial settings setup. [1](#01)
EXECDIR=$(dirname "$0")
if [[ "${EXECDIR}" == "." ]]; then
	cd ../../../../	
fi	
. src/main/resources/scripts/setenv.sh
mvn -e exec:exec -DexecType=settings -Dlibrary.path=-Ddummy=dummy
