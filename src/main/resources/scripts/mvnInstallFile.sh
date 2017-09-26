#!/bin/bash
#DESC: This script will install the single missing library to the local maven cache
export GROUP=$1
export ARTIFACT=$2
export VERSION=$3
export FILE=$4
export PACKAGING=jar
export CLASSIFIER=
if [[ ! "$5" == "" ]]; then
	if [[ ! "$5" == "pom" ]] && [[ ! "$5" == "jar" ]];then
		export CLASSIFIER=-Dclassifier=$5
	else
		export PACKAGING=$5
	fi
fi 
if [[ ! "$6" == "" ]]; then
	export PACKAGING=$6
fi

##[[ "$5" = "sources" ]]       && export CLASSIFIER=-Dclassifier=$5
##[[ "$5" = "javadoc" ]]       && export CLASSIFIER=-Dclassifier=$5
##[[ "$5" = "tests" ]]         && export CLASSIFIER=-Dclassifier=$5
##[[ "$5" = "tests-javadoc" ]] && export CLASSIFIER=-Dclassifier=$5
##[[ "$5" = "pom" ]]           && export PACKAGING=$5
##[[ "$6" = "pom" ]]           && export PACKAGING=$6
##[[ "$6" != "" ]]             && export PACKAGING=$6
##[[ "$5" != "" ]]             && export CLASSIFIER=-Dclassifier=$5
mvn install:install-file -DgroupId=$GROUP \
    -DartifactId=$ARTIFACT \
    -Dversion=$VERSION \
    -Dpackaging=$PACKAGING \
    -Dfile=$FILE \
    $CLASSIFIER \
    -DgeneratePom=true \
    -DcreateChecksum=true
