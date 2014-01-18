/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Frankie Lyon
 */
public class Network {
    
    public static void NetIn() {
        int i = 0;
        while(true) {
            i++;
        try {
            //Network input from Raspberry pi
            SocketConnection sc = (SocketConnection)Connector.open("socket://10.25.57.6:111");
            sc.setSocketOption(SocketConnection.LINGER, 5);
            InputStream is = sc.openInputStream();
            //System.out.println(Byte.toString(is.read())); //replace this line with connection to Vision coordinate values (X, Y)
            System.out.println(is.hashCode());
            System.out.println("YEAH");
            //Robot.X = 
            //Robot.Y = 
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("HITLER WAS AN OKAY GUY");
        }
        Timer.delay(1);
        if (i > 1000) 
            break;           
            System.out.println("...............");
        
    }
    }
            
}
