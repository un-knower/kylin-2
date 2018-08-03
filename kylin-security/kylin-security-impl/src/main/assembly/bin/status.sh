#author jason.wu@ikang.com
#!/bin/sh

cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
TEMP_DIR=$DEPLOY_DIR/temp

CONF_FILE=$CONF_DIR/application.properties

SERVER_NAME=`sed '/^dubbo.server.name/!d;s/.*=//' $CONF_FILE | tr -d '\r'`
SERVER_PORT=`sed '/^dubbo.server.protocol.port/!d;s/.*=//' $CONF_FILE | tr -d '\r'`

if [ -n "$2" ]; then
	SPRING_PROFILE="$2"
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
	fi
fi

if [ -z "$SERVER_NAME" ]; then
	SERVER_NAME="dubbo-server"
fi

PIDS=""
#if [ -n "$SERVER_PORT" ]; then
#	PIDS=`netstat -tlnp | grep $SERVER_PORT|awk '{printf $7}'|cut -d/ -f1`
#fi

if [ -z "$PIDS" ]; then
	PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
fi

if [ -n "$PIDS" ]; then
	echo "INFO: The $SERVER_NAME already started!"
	echo "INFO: The SERVER_NAME		: $SERVER_NAME"
	echo "INFO: The DEPLOY_DIR		: $DEPLOY_DIR"
	echo "INFO: The SERVER_PORT		: $SERVER_PORT"
	echo "INFO: The SPRING_PROFILE	: $SPRING_PROFILE"
	echo "INFO: PID: $PIDS"
	exit 1
fi

echo "ERROR: The $SERVER_NAME does not started!"
exit 1