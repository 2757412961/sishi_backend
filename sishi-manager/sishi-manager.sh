#!/bin/bash
JVM="-server -Xms512m -Xmx1024m \
-XX:MaxNewSize=128m \
-XX:+UseStringDeduplication \
-XX:+UseGCOverheadLimit \
-XX:+ExplicitGCInvokesConcurrent \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:+ExitOnOutOfMemoryError \
-Djava.awt.headless=true \
-XX:+CMSClassUnloadingEnabled \
-Duser.timezone=GMT+08"
#获取项目根目录
APPFILE_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && cd .. && pwd )"
#获取项目名
APP_NAME="$( cd "${APPFILE_PATH}/bin" && ls *.sh | cut -d "." -f 1 )"
#获取主jar文件名
JAR_NAME="$( ls "${APPFILE_PATH}" | grep jar )"
#设置依赖文件
LIB_PATH="${APPFILE_PATH}/lib/*"
#设置配置文件路径
CONF_PATH="${APPFILE_PATH}/conf"
#设置日志文件路径
LOG_PATH="/var/log/sishi/${APP_NAME}"
#初始化进程号
PROCESS_ID=""
#环境名
PROFILE=""
#环境jar包
PROFILE_JAR_NAME=""
#使用说明
usage(){
    echo -e "eg. sh ${APPFILE_PATH}/${APP_NAME}.sh [start|stop|restart|status] [dev|local]"
}
#判断参数的个数是否是三个，并且profile是否在规定范围内
validate_arg(){
    profiles=("dev" "local")
    if [ "$1" -eq "2" ]; then
        for profile in ${profiles[@]}; do
            if [ "$profile" == "$2" ]; then
                return 0
            fi
        done
    fi
    return 1
}
#启动方法
start(){
    if [ -n "$PROCESS_ID" ]
    then
        echo "[Wanring]${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} is already running. pid=${PROCESS_ID} ."
    else
        #根据环境来拷贝一份jar
        cp -f ${APPFILE_PATH}/${JAR_NAME} ${APPFILE_PATH}/${PROFILE_JAR_NAME} > /dev/null 2>&1
        #创建日志文件夹
        if [ ! -d $LOG_PATH  ];then
          mkdir -p $LOG_PATH
          echo "[Success]Create the log path ${LOG_PATH} successfully"
        fi
        #启动jar包
        /usr/bin/nohup java $JVM -Dlogging.config=classpath:log4j.properties -Dspring.profiles.active=${PROFILE} \
        -cp "${CONF_PATH}":"${LIB_PATH}":"${APPFILE_PATH}/${PROFILE_JAR_NAME}" cn.edu.zju.sishi.Application \
        > /var/log/sishi/$APP_NAME/start.log 2>&1 &
        #判断是否启动成功
        if [ $? != 0 ]; then
            echo "[Error]Failed when start ${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE}"
            echo "Please see the detail in /var/log/sishi/${APP_NAME}/start.log"
            exit 1
        else
            echo "[Success]start ${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} successfully"
        fi
    fi
}
#停止方法
stop(){
    if [ -n "$PROCESS_ID" ]
    then
        kill -9 $PROCESS_ID
        echo "[Success]Stop ${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} in pid ${PROCESS_ID} successfully"
    else
        echo "[Warning]${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} is not running"
    fi
}
#重启方法
restart(){
    stop
    PROCESS_ID="$(ps -ef | grep "${APPFILE_PATH}"/"${PROFILE_JAR_NAME}" | grep -v grep | awk '{print $2}')"
    start
}
#输出运行状态
status(){
    if [ -n "$PROCESS_ID" ]
    then
        echo "[Success]${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} is already running. pid=${PROCESS_ID} ."
    else
        echo "[Success]${APPFILE_PATH}/${APP_NAME} with profile ${PROFILE} is not running"
    fi
}
# 具体执行
validate_arg $# $2
if [ "$?" -eq 0 ]; then
    PROFILE=$2
    PROFILE_JAR_NAME=$APP_NAME-$PROFILE".jar"
    PROCESS_ID="$(ps -ef | grep "${APPFILE_PATH}"/"${PROFILE_JAR_NAME}" | grep -v grep | awk '{print $2}')"
    case "$1" in
    "start")
            start
            ;;
    "stop")
            stop
            ;;
    "restart")
            restart
            ;;
    "status")
            status
            ;;
    *)
        usage
        ;;
    esac
else
    usage
fi
exit
