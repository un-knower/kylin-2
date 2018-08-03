#author jason.wu@ikang.com
#!/bin/sh

cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

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

echo -e "INFO: Stopping the $SERVER_NAME ...\c"
if [ -z "$PIDS" ]; then
    echo -e "\nERROR: The $SERVER_NAME does not started!"
    exit 1
fi

for PID in $PIDS ; do
    kill -9 $PID > /dev/null 2>&1
done

COUNT=0
NUM_LOCAL=0
TOTAL=20
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1
    COUNT=1
    for PID in $PIDS ; do
        PID_EXIST=`ps -f -p $PID | grep java`
        if [ -n "$PID_EXIST" ]; then
            COUNT=0
            break
        fi
    done
    let NUM_LOCAL=NUM_LOCAL+1
	if [ $NUM_LOCAL -gt $TOTAL ]; then
		echo -e "\nERROR: The $SERVER_NAME stop failure, Please view the log."
		COUNT=-1
		break
	fi
done

if [ $COUNT -eq 1 ]; then
	echo "OK!";	
fi

echo "INFO: PID: $PIDS"
