#!/bin/bash
. ./setenv.sh 
####################################################################################
# ./mvnDeployFile.sh <groupid> <artifactid> <version> file [clasifier] [packaging]
####################################################################################
# ./mvnDeployFile.sh com.progress.actional common                      8.2.2 ../../../target/lib/common-8.2.2.jar
# ./mvnDeployFile.sh com.progress.actional builtinplugins-intermediary 8.2.2 ../../../target/lib/builtinplugins-intermediary-8.2.2.jar
# ./mvnDeployFile.sh com.progress.actional common-products             8.2.2 ../../../target/lib/common-products-8.2.2.jar
# ./mvnDeployFile.sh com.progress.actional catalogs                    8.2.2 ../../../target/lib/catalogs-8.2.2.jar
# ./mvnDeployFile.sh com.progress.actional soapstation_plugin          8.2.2 ../../../target/lib/soapstation_plugin-8.2.2.jar
# ./mvnDeployFile.sh com.progress.actional sdk-core                    8.2.2 ../../../target/lib/sdk-core-8.2.2.jar
# ./mvnDeployFile.sh com.documentum        configservice-api           6.6   ../../../target/lib/configservice-api-6.6.jar
# ./mvnDeployFile.sh com.documentum        configservice-impl          6.6   ../../../target/lib/configservice-impl-6.6.jar
# ./mvnDeployFile.sh com.documentum        dms-client-api              6.6   ../../../target/lib/dms-client-api-6.6.jar
# ./mvnDeployFile.sh com.documentum        dfc                         6.6   ../../../target/lib/dfc-6.6.jar
