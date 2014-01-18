/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
    RobotDrive drive;
    AnalogChannel ard_X; //analog inputs from arduino
    AnalogChannel ard_Y;
    double X, Y; //translation (strings to doubles) from Pi (from vision class)
    double[] xy; //Pi target coordinate values after moving average filter
    
    
    public static double motor_x; //motor x-value
    public static double motor_y; //motor y-value
    
    public static double lz_X; //localized x-coordinate
    public static double lz_Y; //localized y-coordinate
    public static double t;    //timer value used for acceleration -> position integration 
    Timer time;
    
    public void robotInit() {
        drive   = new RobotDrive(Map.leftDriveMotor, Map.rightDriveMotor);
        ard_X   = new AnalogChannel(Map.arduino_X);
        ard_Y   = new AnalogChannel(Map.arduino_Y);
        xy      = Vision.average(X, Y);
        Network.NetIn();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

   
    public void teleopPeriodic() {
        Network.NetIn();
        
        //timer start/stop operations for localization calculations
        if (motor_x != 0 || motor_y != 0) 
            time.start();
        else if (Math.abs(motor_x) < .05 && Math.abs(motor_y) < .05) 
            time.stop();
            time.reset();
            t = 0;
        
        t = time.get();
        
        
        
    }
    
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}