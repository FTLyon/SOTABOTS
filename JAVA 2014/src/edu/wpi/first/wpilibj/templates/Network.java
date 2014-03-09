/*
Error code 221: Socket could not be opened.
Error code 222: Data stream could not be created.
Error code 223: Could not read boolean.
Error code 224: Could not read String.
*/

package edu.wpi.first.wpilibj.templates;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;
import java.io.IOException;
import java.io.DataInput;

public class Network {
    public int Listenbool(){
        //If error occurs returns value -1
        //If no error occurs returns 1(true) or 0(false)
        SocketConnection socket;
        DataInput data;
        int seen = -1;
        try{
            socket = (SocketConnection) Connector.open("10.25.57.10:111");
        }
        catch(IOException e){
            System.out.println("Error code 221");
            return seen;
        }
        try{
            data = socket.openDataInputStream();
        }
        catch(IOException e){
            System.out.println("Error code 222");
            return seen;
        }
        try{
            if(data.readBoolean()){
                seen = 1;
            }
            else{
                seen = 0;
            }
        }
        catch(IOException e){
            System.out.println("Error code 223");
            return -1;
        }
        return seen;
    }
    public String ListenCoor(){
        //If error ocurrs returns value "-2,-2"
        //If no error occurs returns "x,y" or "-1,-1" if nothing found
        SocketConnection socket;
        DataInput data;
        String seen = "-2,-2";
        try{
            socket = (SocketConnection) Connector.open("10.25.57.10:111");
        }
        catch(IOException e){
            System.out.println("Error code 221");
            return seen;
        }
        try{
            data = socket.openDataInputStream();
        }
        catch(IOException e){
            System.out.println("Error code 222");
            return seen;
        }
        try{
            return data.readUTF();
        }
        catch(IOException e){
            System.out.println("Error code 224");
            return "-2,-2";
        }
    }
}
