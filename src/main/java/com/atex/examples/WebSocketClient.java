package com.atex.examples;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

// For later. Register client encoders and decoders for JSON message format
// @ClientEndpoint( encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class } )
@ClientEndpoint
public class WebSocketClient {
    private static final Logger log = Logger.getLogger(WebSocketClient.class.getName() );
    private Session mySession;
    private long messageCount = 0;
    
    @OnOpen
    public void onOpen( final Session session ) throws IOException, EncodeException {
        log.info("Socket client got 'open' event");
        mySession = session;
    }

    @OnMessage
    public void onMessage( final String message) {
        messageCount++;
        log.finest( String.format( "Received message '%s'", message));
        log.log(Level.INFO, "TOTAL MESSAGES RECEVIED: {0}", messageCount);
    }
    
    /**
     * Callback hook for Connection close events.
     * 
     * @param userSession
     *            the userSession which is getting closed.
     * @param reason
     *            the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.mySession = null;
        log.log(Level.INFO, "Session closed. Reason: {0}", reason.getReasonPhrase());
    }
 
    @OnError
    public void onError(Session session, Throwable t) {
        log.log(Level.SEVERE, "ERROR TERROR: {0}", t.getMessage());
    }
    
    public long getMessageCount() {
        return messageCount;
    }

}
