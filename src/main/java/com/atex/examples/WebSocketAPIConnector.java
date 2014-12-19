package com.atex.examples;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

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

    private static final Logger logger = Logger.getLogger(WebSocketAPIConnector.class.getName());

    private final String id = UUID.randomUUID().toString().substring(0, 8);
    private final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    private final String apiUrl = "ws://NOT SET";
    private Properties appConf;
    public static final String DEFAULT_PROPS_FILENAME = "appConf.properties";
    private static final String PROPS_FILENAME_FORMAT = "appConf_%s.properties";
    private Session socketSession;
    /**
     * Use: -Denv=prod or -Denv=test
     */
    public static final String ENVIRONMENT_NAME_KEY = "env";

    /**
     * Connect to the API endpoint on application startup. 
     * Looks at system property 'env' and loads the corresponding appConf_[%env].properties file from classpath.
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        String apiEndpoint = appConf.getProperty("apiEndpoint");
        String apiKey = appConf.getProperty("apiKey");
        String msgFilter = appConf.getProperty("msgFilter"); // No filter -> ALL click events
        
        logger.log(Level.FINEST, "Conneting to apiEndpoint at {0}", apiEndpoint);
        try (Session session = container.connectToServer(WebSocketClient.class, URI.create(apiEndpoint))) {
            session.getBasicRemote().sendObject(makeConnectMessage(id, apiKey, msgFilter)); // Connect and authorize
            logger.log(Level.INFO, "Connected to apiEndpoint at {0}", apiEndpoint);
            socketSession = session;
        } catch (DeploymentException e0) {
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Connection failed to: " + apiUrl, e0);
            logger.log(Level.SEVERE, "Could not connect to apiEndpoint at {0}", apiEndpoint);
        } catch (EncodeException | IOException e1) {
            event.getServletContext().log("WebSocketAPIConnector::contextInitialized - Couldn't connect to: " + apiUrl, e1);
            logger.log(Level.SEVERE, "Could not connect to apiEndpoint at {0}", apiEndpoint);
        }
    }

    /**
     * Tear down on application shutdown
     *
     * @param event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            socketSession.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Closing websocket session failed", ex);
        }
    }

    private String makeConnectMessage(String id, String key, String filter) {
        return "{ client: '" + id + "'"
                + ", apiKey: '" + key + "'"
                + ", channel: 'clickStream' "
                + ", msgFilter: '" + filter + "'}";
    }

    @PostConstruct
    public void initEnvironmentProps() {
        appConf = new Properties();
        String propsFilename = DEFAULT_PROPS_FILENAME;
        String environmentName = System.getProperty(ENVIRONMENT_NAME_KEY, "prod");
        if (environmentName != null) {
            propsFilename = String.format(PROPS_FILENAME_FORMAT, environmentName);
        }
        logger.log(Level.INFO, "Reading {0}", propsFilename);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propsFilename);
        if (inputStream == null) {
            throw new RuntimeException(new FileNotFoundException("Properties file " + propsFilename + " not found in the classpath."));
        }
        try {
            appConf.load(inputStream);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read " + propsFilename, ex);
            throw new RuntimeException(ex);
        }
    }
}
