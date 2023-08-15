FROM openjdk:8-jdk

VOLUME /tmp

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/rc-config-server.jar app.jar

#
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=sit -Dspring.cloud.nacos.config.server-addr=192.168.0.53:8848 -jar /app.jar"]
