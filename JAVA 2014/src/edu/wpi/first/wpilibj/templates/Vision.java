package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.Robot.*;
import java.util.*;
/**
 *
 * @author Frankie Lyon
 */
public class Vision {
    static double sumX;
    static double sumY;
    static double[] arrayX = new double[200];
    static double[] arrayY = new double[200];
    static int index = 0;
    
    public static double x_target;
    public static double y_target;
    public static double tolerance_x;
    public static double tolerance_y;
    
    public static double[][] gather (String in) {
        //Input looks like this: [[x y][x y]]
        String temp1 = in.replace('[', ' ');
        // x y] x y]]
        String temp2 = temp1.replace(']', ' ');
        // x y  x y  
        String[] list1 = Split(temp2, "  ");
        // list1 = [x y][x y]
        String temp3 = null; //x y
        String temp4 = null;
        
        String[] list3;
        String[] list4;
        if (list1[0] != null)
            temp3 = list1[0];
            list3 = Split(temp4, " ");
        if (list1[1] != null)
            temp4 = list1[1]; //x y
            list4 = Split(temp4, " ");
            
        //We now have: list3 = {x,y} list4 = {x,y}
        double[] coor1 = null;
        double[] coor2 = null;
        
        for (int i = 0; i < list3.length; i++) {
            if (list3 != null)
                coor1[i] = Double.parseDouble(list3[i]);
            if (list4 != null)
            coor2[i] = Double.parseDouble(list4[i]);
        }
        // coor1 = [x1,y1]
        // coor2 = [x2,y2]
        
        
        
        double coor[][] = null; //Two-dimensional array for: [0][0] = x1, [0][1] = y1, [1][0] = x2, [1][1] = y2
        coor[0][0] = coor1[0]; //x1
        coor[0][1] = coor1[1]; //y1
        coor[1][0] = coor2[0]; //x2
        coor[1][1] = coor2[1]; //y2
        return coor;
    }
    
    public static double[] average (double inputX, double inputY) {
        if (index < arrayX.length - 1) {
            arrayX[index] = inputX;
            arrayY[index] = inputY;
            index++;
        }
        else {
            index = 0;
        }
        for (int i = 0; i < arrayX.length - 1; i++) {
            sumX += arrayX[i];
            sumY += arrayY[i];
        }
        
        double[] coordinates = new double[2];
        coordinates[0] = sumX / 200;
        coordinates[1] = sumY / 200;
        return coordinates;
    }
    
    public static boolean leftRight (double[] c1, double[] c2) {
        boolean leftRight = false; //false -> left; true -> right;
        
        if (c1 != null && c2 != null) {
            Timer.delay(1);
        }
        else if (c1 == null) {
            //Pass
        }
        else if (c1 != null) {
            target(c1);
        }
        
        return leftRight;
    }
    
    public static void target(double[] coor) {
        double x = coor[0];
        double y = coor[1];
        
        //if target is on LEFT SIDE and outside tolerance
        while (x < x_target - tolerance_x) {
            Robot.motor_x = .1 * (x_target - x);
        }
        
        //if target is on RIGHT SIDE and outside tolerance
        while (x > x_target + tolerance_x) {
            Robot.motor_x = .1 * (x - x_target);
        }
        
        //if target is ABOVE and outside tolerance (INVERT Y-COORDINATE FIRST!!!)
        while (y < y_target - tolerance_y) {
            Robot.motor_y = .1 * (y_target - y);
        }
        
        //if target is BELOW and outside tolerance 
        while (y > y_target + tolerance_y) {
            Robot.motor_y = .1 * (y - y_target);
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