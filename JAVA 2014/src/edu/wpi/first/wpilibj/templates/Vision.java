package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.Robot.*;

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
    
}