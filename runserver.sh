#!/bin/sh

#===========================================================================================
# Java Environment Setting
#===========================================================================================



#===========================================================================================
# JVM Configuration
#===========================================================================================
#JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"
#JAVA_OPT="${JAVA_OPT} -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8 -XX:+DisableExplicitGC"
#JAVA_OPT="${JAVA_OPT} -verbose:gc -Xloggc:${HOME}/rmq_srv_gc.log -XX:+PrintGCDetails"
JAVA_OPT="${JAVA_OPT} -Duser.timezone=GMT+8 -XX:-OmitStackTraceInFastThrow -Dnetworkaddress.cache.ttl=60"
JAVA_OPT="${JAVA_OPT} -Djava.awt.headless=true -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"

java ${JAVA_OPT} -jar commandService.jar 2>&1$@
