/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atex.examples;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class WebSocketClientTest {

    @Test
    public void testOnMessage() {
        String message = "{ whatever: 'value' }";
        WebSocketClient instance = new WebSocketClient();
        instance.onMessage(message);
        assertEquals("Message count should be 1", 1L, instance.getMessageCount());
    }

    @Test
    public void testGetMessageCount() {
        System.out.println("getMessageCount");
        WebSocketClient instance = new WebSocketClient();
        long expResult = 0L;
        long result = instance.getMessageCount();
        assertEquals("Message count should be 0", 0L, result);
        instance.onMessage("whatever");
        assertEquals("Message count should be 1", 1L, instance.getMessageCount());
    }
    
}
