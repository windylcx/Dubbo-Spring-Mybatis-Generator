#!/usr/bin/env bash
APP=generator-web-1.0.0-SNAPSHOT
JAR=$APP.jar
FULLPATH=$(cd "$(dirname "$0")/../"; pwd)


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



function doStop(){
	echo "stopping $APP..";
	xargs -a $FULLPATH/PID.txt kill -9
	for i in $(seq 10)
	do
		if [ -f $FULLPATH/PID.txt ];then
			echo -e ".\c"
		else
			banner "$APP stopped."
			rm -f $FULLPATH/PID.txt
			exit 0
		fi
		sleep 1
	done
	banner "Stop $APP failed ! please check. PID is : "`cat $FULLPATH/PID.txt`

}


isRunning

if test $? -eq 0;then
	banner "$APP already stopped: "
else
	doStop
fi
