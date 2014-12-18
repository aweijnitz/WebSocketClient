# Minimal Web Socket Client WAR scaffold
Minimal Maven scaffold for trying out web applications as web socket clients. Based on the
Maven [Standard Directory Layout](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)

Includes embedded
[Maven Jetty plugin](http://www.eclipse.org/jetty/documentation/9.2.2.v20140723/jetty-maven-plugin.html)
(so, hot relaod on per default).

## Installation
- Clone this repo
- ```mvn clean install```

## Running
```mvn jetty:run```

## WebSockets?
Some startng points

- [javax.websocket](https://javaee-spec.java.net/javadocs/javax/websocket/package-summary.html)
- [javax.websocket.ClientEndpoint](https://javaee-spec.java.net/javadocs/javax/websocket/ClientEndpoint.html)
- [Small tutorial](http://java.dzone.com/articles/java-websockets-jsr-356-jetty) 
