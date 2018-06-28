FROM websphere-liberty:microProfile
COPY /target/liberty/wlp/usr/servers/defaultServer/server.xml /config/server.xml
RUN installUtility install --acceptLicense defaultServer
COPY /target/liberty/wlp/usr/servers/defaultServer /config/
COPY /target/liberty/wlp/usr/shared/resources /config/resources/