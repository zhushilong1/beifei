# 选择tomcat
FROM 192.168.13.129:12345/tomcat:8.5-jre8
RUN rm -rf /usr/local/tomcat/webapps/*
ADD ./target/backup.war /usr/local/tomcat/webapps/ROOT.war
RUN bash -c 'touch /usr/local/tomcat/webapps/ROOT.war'
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]