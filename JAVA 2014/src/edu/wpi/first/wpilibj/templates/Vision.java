package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

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
            //Target
        }
        
        return leftRight;
    }
    
    
}