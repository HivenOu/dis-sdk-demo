#基础镜像，一切从这里开始,运行war包需要用到tomcat，版本8.5
FROM tomcat:8.5
#将本地war包拷贝到基础镜像对应的目录下
ADD target/dis.war /usr/local/tomcat/webapps/dis.war
#镜像构建时需要运行的项目时区调整为中国，上海
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime  &&  echo "Asia/Shanghai" > /etc/timezone