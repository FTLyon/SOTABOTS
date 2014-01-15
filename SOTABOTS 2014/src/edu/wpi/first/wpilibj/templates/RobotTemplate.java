/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    RobotDrive drive;
    DigitalInput pi_X;
    DigitalInput pi_Y;
    DigitalOutput out;
    double x_accel_0;
    double y_accel_0;
    
    
    public void robotInit() {
        drive   = new RobotDrive(Map.leftDriveMotor, Map.rightDriveMotor);
        pi_X    = new DigitalInput(Map.piInput_X);
        pi_Y    = new DigitalInput(Map.piInput_Y);
        
       
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        try {          
            SocketConnection sc = (SocketConnection)Connector.open("socket://GLOBAL-2012:10.25.57.6");
            sc.setSocketOption(SocketConnection.LINGER, 5);
            InputStream is = sc.openInputStream();
            System.out.println(is.read());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IAN's FAULT");
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
