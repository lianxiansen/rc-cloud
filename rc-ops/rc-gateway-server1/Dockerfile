FROM openjdk:8-jdk

VOLUME /tmp

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/rc-gateway-server.jar app.jar

#
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=sit -Dspring.cloud.nacos.config.server-addr=rc-cloud-nacos.rc-cloud:8848 -Dspring.cloud.nacos.config.namespace='fb75af1d-309a-4a04-853c-c51f229cc661' -jar /app.jar"]
