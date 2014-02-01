/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Frankie Lyon
 */
public class Accelerometer {
    
    static double sumX;
    static double sumY;
    static double[] arrayX = new double[200];
    static double[] arrayY = new double[200];
    static int index = 0;
    
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
        
        double[] values = new double[2];
        values[0] = sumX / 200;
        values[1] = sumY / 200;
        return values;
    }
    
    public static void localize (double accelX, double accelY) {
        
        if (Robot.t != 0) {
            Robot.lz_X += accelX * .5 * Robot.t * Robot.t;
            Robot.lz_Y += accelY * .5 * Robot.t * Robot.t;
        }
    }
    
}
