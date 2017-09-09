	# Required for maven 2
#export  M2=/usr/local/lib/apache-maven-2.2.1
export  M2=/usr/local/maven
export  M2_HOME=$M2
export  M3=$M2
export  M3_HOME=$M2
export  JAVA_HOME=/usr/lib/jvm/java-8-oracle
export  PATH=$M2/bin:$JAVA_HOME/bin:$PATH
export  MVN_OPTS=-ea
# required for the deploy-file goal
export  REPO_URL=http://
export  REPO_ID=
export  ARGS=$*
# echo $ARGS