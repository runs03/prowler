FROM tomcat:8-jdk8
ADD ./target/ProwlerApp.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]