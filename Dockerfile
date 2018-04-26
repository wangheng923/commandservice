FROM openjdk:8-jdk

RUN rm -rf /etc/localtime && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo " export LANG=zh_CN.UTF-8" >> /root/.bashrc && export LANG=zh_CN.UTF-8 && export LC_ALL=zh_CN.UTF-8
COPY commandService.jar /usr/lib/commandService/commandService.jar
COPY runserver.sh /usr/lib/commandService/runserver.sh
WORKDIR /usr/lib/commandService
RUN ["chmod", "+x", "runserver.sh"]
CMD ["/usr/lib/commandService/runserver.sh"]
VOLUME /opt/logs \
       /opt/store
EXPOSE 8080
