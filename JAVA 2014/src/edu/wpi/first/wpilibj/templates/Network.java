/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedReader;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Byte;
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
        try {
            //Network input from Raspberry pi
            SocketConnection sc = (SocketConnection)Connector.open("socket://10.25.57.6:111");
            sc.setSocketOption(SocketConnection.LINGER, 1);
            InputStream is = sc.openInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            System.out.println(in.readLine());
            System.out.println("YEAH");
            Timer.delay(.01);
            //Robot.X = 
            //Robot.Y = 
        } catch (IOException ex) {
            System.out.println("Recieve data failed.");
            Timer.delay(.1);
        }
        
    }
    }
}
            

