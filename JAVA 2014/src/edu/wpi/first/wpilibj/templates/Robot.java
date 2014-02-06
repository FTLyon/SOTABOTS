package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends SimpleRobot {
    Joystick   leftStick            = new Joystick(1);
    Joystick   rightStick           = new Joystick(2);
    RobotDrive drive                = new RobotDrive(1,2);
    Talon      intakeMotor          = new Talon(3);
    Talon      wench                = new Talon(4);
    Compressor compressor           = new Compressor(14,1);
    DigitalInput lim_switch         = new DigitalInput(1);
    Solenoid   intake_1             = new Solenoid(1);
    Solenoid   intake_2             = new Solenoid(2);
    Solenoid   shift_1              = new Solenoid(3);
    Solenoid   shift_2              = new Solenoid(4);
    Solenoid   lock_1               = new Solenoid(5);
    Solenoid   lock_2               = new Solenoid(6);
    Timer      time_1               = new Timer();
    
    boolean    intakeDown           = false;
    boolean    intake               = false;
    boolean    winchDown            = false;
    boolean    shifted              = false;
    boolean    locked               = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
   
    public void autonomous() {
        
    }

    public void operatorControl() {
        compressor.start();
        
        while (isOperatorControl() && isEnabled()) {
            if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) > 0.15)           
                drive.arcadeDrive(leftStick);
            else if (Math.abs(leftStick.getAxis(Joystick.AxisType.kY)) > 0.15)
                drive.arcadeDrive(leftStick);
            else
                drive.arcadeDrive(0,0);
            wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                       
/*left*/    if (leftStick.getRawButton(6)) 
                shifted = true;
            else if (leftStick.getRawButton(7)) 
                shifted = false;
            
/*right*/   if (rightStick.getRawButton(3)) 
                winchDown = true;
            
            if (rightStick.getRawButton(7) && intake == false)
                intake    = true;
            else if (rightStick.getRawButton(7) && intake == true) 
                intake    = false;
            
            if (rightStick.getRawButton(2) && rightStick.getTrigger()) 
                locked = false;
            
////////////////////////////////////////////////////////////////////////////////
            
/*winch*/   if (winchDown = true && lim_switch.get() == false) 
                wench.set(.4);            
            else if (lim_switch.get()) 
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                winchDown = false;
                locked = true;
            
/*lock*/    if (locked) {
                lock_1.set(true);
                lock_2.set(false);}
            else if (locked == false) 
                lock_1.set(false);
                lock_2.set(true);
            
/*intake-m*/if (intake) 
                intakeMotor.set(1);
            else if (intake == false) 
                intakeMotor.set(0);
            
/*shifters*/if (shifted) {
                shift_1.set(true);
                shift_2.set(false);}
            else 
                shift_1.set(false);
                shift_2.set(true);
            
            Timer.delay(.01);
            
        }
    }
    
    public void test() {
    
    }
    
    
}