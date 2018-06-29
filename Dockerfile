FROM websphere-liberty:microProfile
MAINTAINER chrisesharp@gmail.com
COPY /scoreboardService/target/liberty/wlp/usr/servers/defaultServer/server.xml /config/server.xml
RUN installUtility install --acceptLicense defaultServer
COPY /scoreboardService/target/liberty/wlp/usr/servers/defaultServer /config/
COPY /scoreboardService/target/liberty/wlp/usr/shared/resources /config/resources/