#!/usr/bin/env bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd ${DIR}
mvn -e versions:set \
    -DgroupId=com.funny \
    -DartifactId=study-middleware \
    -DnewVersion=1.0.0-SNAPSHOT \
    -DgenerateBackupPoms=false
##
## 每次需要升级release版本时,改下上面版本号,执行脚本即可.
##
## 只是修改所有pom的版本号,不会deploy,需要手动deploy.
