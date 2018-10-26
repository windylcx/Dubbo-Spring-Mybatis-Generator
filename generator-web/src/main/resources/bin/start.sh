#!/usr/bin/env bash
APP=generator-web-1.0.0-SNAPSHOT
JAR=$APP.jar
FULLPATH=$(cd "$(dirname "$0")/../"; pwd)


JAVA_MEM_OPTS=""
BITS=`file $JAVA_HOME/bin/java | grep 64-bit`
if [ -n "$BITS" ]; then
    let memTotal=`cat /proc/meminfo |grep MemTotal|awk '{printf "%d", $2/1024 }'`
    if [ $memTotal -gt 2500 ];then
        JVMARGS=" -server -Xmx512m -Xms256m -Xmn128m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    else
        JVMARGS=" -server -Xmx512m -Xms256m -Xmn128m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    fi
else
	JVMARGS=" -server -Xms256m -Xmx512m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

ALL_ENV=("dev" "local" "pre" "product" "test")
if [ ! -n "$1" ] ;then
    echo "you have not input a env!"
    echo ${ALL_ENV[@]}
    exit 1
fi

ENV=$1
if echo "${ALL_ENV[@]}" | grep -v "$ENV" &>/dev/null; then
    echo "not in env"
    echo ${ALL_ENV[@]}
    exit 1
fi

function banner(){
    echo ""
    echo "--------------------------------------------------------------------------------------------------"
    echo "$1"
    echo "--------------------------------------------------------------------------------------------------"
    echo ""
}


function isRunning(){
    if [ -f $FULLPATH/PID.txt ];then
        num=`xargs -a $FULLPATH/PID.txt ps -p | wc -l`
       	if [ $num -eq  2 ]; then
			return 1;
		else
			return 0;
		fi
	else
        return 0
        fi
}


function doStart(){
	rm -f $FULLPATH/PID.txt
	nohup java -jar $JVMARGS $FULLPATH/$JAR --spring.profiles.active=$ENV 1>/dev/null 2>$FULLPATH/error.log &
	for i in $(seq 10)
	do
		if [ -f $FULLPATH/PID.txt ];then
			banner "$APP started, PID is : "`cat $FULLPATH/PID.txt`
			exit 0;
		else
			echo -e ".\c"
		fi
		sleep 1
	done
	banner "start $APP fail"
}



isRunning

if test $? -eq 0;then
	banner "starting $APP"
	doStart
else
	banner "$APP already started..pid: "`cat $FULLPATH/PID.txt`
fi
