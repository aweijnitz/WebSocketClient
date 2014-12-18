package com.atex.examples;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

// For later. Register client encoders and decoders for JSON message format
// @ClientEndpoint( encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class } )
@ClientEndpoint
public class WebSocketClient {
    private static final Logger log = Logger.getLogger(WebSocketClient.class.getName() );
    
    @OnOpen
    public void onOpen( final Session session ) throws IOException, EncodeException {
        session.getBasicRemote().sendObject( "Client connect! " );
    }

    @OnMessage
    public void onMessage( final String message ) {
        log.info( String.format( "Received message '%s'",
                                 message));
    }
}
