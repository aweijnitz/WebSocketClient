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

## Application notes
This is a plain JEE7 Web Application. At startup it creates a
websocket connection using a ServletContextListener. It passes the
class WebSocketClient as ClientEndpoint. The ClientEndpoint handles
all incoming events.

- The start class ```src/main/java/com/atex/examples/WebSocketAPIConnector.java```
- The event handler: ```src/main/java/com/atex/examples/WebSocketClient.java```

## WebSockets?
Some startng points

- [javax.websocket](https://javaee-spec.java.net/javadocs/javax/websocket/package-summary.html)
- [javax.websocket.ClientEndpoint](https://javaee-spec.java.net/javadocs/javax/websocket/ClientEndpoint.html)
- [Small tutorial](http://java.dzone.com/articles/java-websockets-jsr-356-jetty) 
