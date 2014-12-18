package com.atex.examples;

import java.net.URI;
import java.util.UUID;
import java.io.IOException;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;


import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
    
//import org.eclipse.jetty.websocket.jsr356.ClientContainer;


@WebListener
public class WebSocketAPIConnector implements ServletContextListener {
    
    final String id = UUID.randomUUID().toString().substring( 0, 8 );
    private final WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
    private final String apiUrl = "ws://NOT SET";

    
    /**
     * Connect to the API endpoint on application startup
     * TODO: Read URL from config
     */
    public void contextInitialized(ServletContextEvent event) {

        try( Session session = container.connectToServer( WebSocketClient.class, URI.create(apiUrl))) {
            session.getBasicRemote().sendObject( "Client connect: " + id );
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Connected to API endpoint at: " + apiUrl);
        } catch(DeploymentException e0) {
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Connection failed to: " + apiUrl, e0);
        }
        catch(EncodeException e1) {
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Couldn't connect to: " + apiUrl, e1);
        }
        catch(IOException e2) {
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Couldn't connect to: " + apiUrl, e2);
        }

    }

    /** Tear down on application shutdown
     */
    public void contextDestroyed(ServletContextEvent event) {
        //do on application destroy
        //        ( ( ClientContainer )container ).stop();
    }
}
