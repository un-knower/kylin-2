#author jason.wu@ikang.com
#!/bin/bash

cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
TEMP_DIR=$DEPLOY_DIR/temp

CONF_FILE=$CONF_DIR/application.properties

SERVER_NAME=`sed '/^dubbo.server.name/!d;s/.*=//' $CONF_FILE | tr -d '\r'`
SERVER_PORT=`sed '/^dubbo.server.protocol.port/!d;s/.*=//' $CONF_FILE | tr -d '\r'`
LOGS_DIR=`sed '/^log.dir/!d;s/.*=//' $CONF_FILE | tr -d '\r'`
SPRING_PROFILE=`sed '/^spring.profiles.active/!d;s/.*=//' $CONF_FILE | tr -d '\r'`

if [ -n "$1" ]; then
	SPRING_PROFILE="$1"
fi
if [ -z "$SPRING_PROFILE" ]; then
	SPRING_PROFILE="functional"
fi

if [ -n "$SPRING_PROFILE" ]; then
	CONF_FILE=$CONF_DIR/application.$SPRING_PROFILE.properties
	if [ -s "$CONF_FILE" -a -r "$CONF_FILE" ]; then
		if [ -n "$T_SERVER_NAME" ]; then
			SERVER_NAME="$T_SERVER_NAME"
		fi
		if [ -n "$T_SERVER_PORT" ]; then
			SERVER_PORT="$T_SERVER_PORT"
		fi
		if [ -n "$T_LOGS_DIR" ]; then
			LOGS_DIR="$T_LOGS_DIR"
		fi
		if [ -n "$T_SPRING_PROFILE" ]; then
			SPRING_PROFILE="$T_SPRING_PROFILE"
		fi
	fi
fi

PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
	echo "ERROR: The $SERVER_NAME already started!"
	echo "INFO:  PID: $PIDS"
	exit 1
fi

if [ -n "$SERVER_PORT" ]; then
	SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
	if [ $SERVER_PORT_COUNT -gt 0 ]; then
		echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
		exit 1
	fi
fi

if [ -z "$SERVER_NAME" ]; then
	SERVER_NAME="dubbo-server"
fi

if [ -z "$LOGS_DIR" ]; then
	LOGS_DIR="$DEPLOY_DIR/logs"
fi

if [ ! -d $LOGS_DIR ]; then
	mkdir $LOGS_DIR
	chmod 775 $LOGS_DIR
fi

if [ ! -d $TEMP_DIR ]; then
	mkdir $TEMP_DIR
	chmod 775 $TEMP_DIR
fi

LOG_FILE=$CONF_DIR/logback.xml
STDOUT_FILE=$DEPLOY_DIR/dubbo.out

LIB_DIR=$DEPLOY_DIR/lib

#DUBBO_OPTS="-classpath $CONF_DIR:$LIB_JARS -Dlogback.configurationFile=$LOG_FILE"

SPRING_OPTS="$SPRING_PROFILE"
#if [ -n "$SPRING_PROFILE" ]; then
#	SPRING_OPTS="-Dspring.profiles.active=$SPRING_PROFILE"
#fi

JAVA_OPTS="-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true"

if [ "$SPRING_OPTS"x == "prod"x ]; then
	JAVA_MEM_OPTS="-server -Xms1024m -Xmx1024m -XX:PermSize=256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"
	LOG_FILE=$CONF_DIR/logback.prod.xml
else
	JAVA_MEM_OPTS="-server -Xms256m -Xmx800m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"
fi

# file is exists
if [ ! -f "$LOG_FILE" ]; then
    LOG_FILE=$CONF_DIR/logback.xml
fi

DUBBO_OPTS="-Djava.ext.dirs=$LIB_DIR:$JAVA_HOME/jre/lib/ext/ -Dlogback.configurationFile=$LOG_FILE"

RUN_JARS=`ls $DEPLOY_DIR|grep .jar$ |grep impl |grep -v javadoc |grep -v sources`

#echo "$JAVA_MEM_OPTS"
echo -e "INFO: Starting the $SERVER_NAME ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $DUBBO_OPTS -jar $RUN_JARS $SPRING_OPTS > $STDOUT_FILE 2>&1 &

COUNT=0
NUM_LOCAL=0
TOTAL=120
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    if [ -n "$SERVER_PORT" ]; then
        COUNT=`netstat -an | grep $SERVER_PORT | wc -l`
    else
        COUNT=`ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
    fi
    if [ $COUNT -gt 0 ]; then
    	echo "OK!"
		PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
		echo "INFO: PID: $PIDS"
        break
    fi
    let NUM_LOCAL=NUM_LOCAL+1
    if [ $NUM_LOCAL -gt $TOTAL ]; then
    	echo "ERROR: The $SERVER_NAME startup failure, Please view the boot log."
    	break
    fi
done

echo "INFO: The SPRING_PROFILE	: $SPRING_PROFILE"
echo "INFO: STDOUT: $STDOUT_FILE"