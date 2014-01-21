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
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Frankie Lyon
 */
public class Network {
    
    public static void NetIn() {
        int i = 0;
        String[] xy = null;
        while(true) {
        try {
            //Network input from Raspberry pi
            SocketConnection sc = (SocketConnection)Connector.open("socket://10.25.57.6:111");
            sc.setSocketOption(SocketConnection.LINGER, 1);
            InputStream is = sc.openInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            xy = Split(in.readLine().toString(), " ");
            Robot.X = Integer.parseInt(xy[0]);
            Robot.Y = Integer.parseInt(xy[1]);
            Timer.delay(.01);
        } catch (IOException ex) {
            System.out.println("Recieve data failed.");
            Timer.delay(.1);
        }
        
    }
    }
    
    public static String[] Split(String splitStr, String delimiter) {  
    StringBuffer token = new StringBuffer();  
    Vector tokens = new Vector();  
    // split  
    char[] chars = splitStr.toCharArray();  
    for (int i=0; i < chars.length; i++) {  
        if (delimiter.indexOf(chars[i]) != -1) {  
            // we bumbed into a delimiter  
            if (token.length() > 0) {  
                tokens.addElement(token.toString());  
                token.setLength(0);  
            }  
        } else {  
            token.append(chars[i]);  
        }  
    }  
    // don't forget the "tail"...  
    if (token.length() > 0) {  
        tokens.addElement(token.toString());  
    }  
    // convert the vector into an array  
    String[] splitArray = new String[tokens.size()];  
    for (int i=0; i < splitArray.length; i++) {  
        splitArray[i] = (String) tokens.elementAt(i);  
    }  
    return splitArray;  
}  
}
            

