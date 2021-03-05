#base image
FROM java:8
#create by
MAINTAINER zittur
#add tar.gz to image
ADD *.tar.gz /sishi/
#work directory
WORKDIR /sishi/sishi-manager
#environment
ENV PROFILE="dev"
#expose port
EXPOSE 7666
#the entry point of docker
ENTRYPOINT java -server -Xms512m -Xmx512m -XX:MaxNewSize=128m -XX:+UseStringDeduplication -XX:+UseGCOverheadLimit -XX:+ExplicitGCInvokesConcurrent -XX:+HeapDumpOnOutOfMemoryError -XX:+ExitOnOutOfMemoryError -Djava.awt.headless=true -XX:+CMSClassUnloadingEnabled -Dlogging.config=classpath:log4j2.xml -Dspring.profiles.active=${PROFILE} -Duser.timezone=GMT+08 -cp /sishi/sishi-manager/conf:/sishi/sishi-manager/lib/*:/sishi/sishi-manager/sishi-manager-1.0-SNAPSHOT.jar cn.edu.zju.sishi.Application