#!/bin/bash
#DESC: This script will deploy the single missing library to the remote server
export GROUP=$1
export ARTIFACT=$2
export VERSION=$3
export FILE=$4
export PACKAGING=jar
export CLASSIFIER=
[[ "$5" = "sources" ]]       && export CLASSIFIER=-Dclassifier=$5
[[ "$5" = "javadoc" ]]       && export CLASSIFIER=-Dclassifier=$5
[[ "$5" = "tests" ]]         && export CLASSIFIER=-Dclassifier=$5
[[ "$5" = "tests-javadoc" ]] && export CLASSIFIER=-Dclassifier=$5
[[ "$5" = "pom" ]]           && export PACKAGING=$5
[[ "$6" = "pom" ]]           && export PACKAGING=$6

mvn deploy:deploy-file -Durl=$REPO_URL \
    -DrepositoryId=$REPO_ID \
    -DgroupId=$GROUP \
    -DartifactId=$ARTIFACT \
    -Dversion=$VERSION \
    -Dpackaging=$PACKAGING \
    -Dfile=$FILE \
    $CLASSIFIER \
    -DgeneratePom=true
