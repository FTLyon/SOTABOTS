package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class RobotTemplate extends SimpleRobot {
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
    Encoder    winchEncoder         = new Encoder(6,7);
    Encoder    drive_1              = new Encoder(2,3);
    Encoder    drive_2              = new Encoder(4,5);
    DigitalOutput mode_1            = new DigitalOutput(8);
    DigitalOutput mode_2            = new DigitalOutput(9);   
    DigitalOutput mode_3            = new DigitalOutput(10);  
    DigitalOutput mode_4            = new DigitalOutput(11);
    DigitalOutput mode_5            = new DigitalOutput(12);//
    
    boolean    intakeDown           = true;
    boolean    intake               = false;
    boolean    shifted              = false;
    boolean    shoot_1              = false;
    boolean    shoot_2              = false;
    boolean    shoot_3              = false;
    boolean    pressed              = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
    double     fiveRev              = -1905;
    
    
    
    
   
    public void autonomous() {
        //mode_1.set(true);
    }

    public void operatorControl() {
        compressor.start();
        winchEncoder.start();
        //mode_1.set(false);
        while (isOperatorControl() && isEnabled()) {
            //Network.NetIn();
            
            if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) > 0.15)   {        
                drive.arcadeDrive(leftStick);}
            else if (Math.abs(leftStick.getAxis(Joystick.AxisType.kY)) > 0.15) {
                drive.arcadeDrive(leftStick);}
            else{
                drive.arcadeDrive(0,0);}
            
                                   
/*left*/    if (leftStick.getRawButton(6)) {
                shifted = true;}
            else if (leftStick.getRawButton(7)) {
                shifted = false;}
            
            
            else if (leftStick.getRawButton(2)) {
                intakeDown = false;
            }
            
            if (leftStick.getRawButton(4)) {
                System.out.println(winchEncoder.get());
            }
            
/*auto vision*/            
            if (leftStick.getRawButton(8) && leftStick.getRawButton(9)) {
                //target code here, pull from Vision class
            }
            
/*right*/   if (rightStick.getRawButton(3)) {
                winchDown = true;}
            else {
                winchDown = false;
            }           
            if (rightStick.getRawButton(7)) {
                intake = true;
            }
            else if (rightStick.getRawButton(6)) {
                intakeMotor.set(-1);
            }
            else {
                intake = false;
            }
             
            if (rightStick.getTrigger() && rightStick.getRawButton(2)) {
                locked = false;
                winchDown = false;
                winchEncoder.reset();
            }
            if (rightStick.getRawButton(3) && lim_switch.get() == true) {
                wench.set(-.5);
            }
            else if (lim_switch.get() == false && rightStick.getRawButton(3)) {
                winchEncoder.reset();
                lock_1.set(false);
                lock_2.set(true);
            }
            else if (lim_switch.get() == false && winchEncoder.get() > -1000) {
                pressed = true;
                lock_1.set(false);
                lock_2.set(true);
                wench.set(.4);
            }
            else if (rightStick.getTrigger()) {
                pressed = false;
                lock_1.set(true);
                lock_2.set(false);
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
            }
            else if (pressed && winchEncoder.get() <= -1000) {
                lock_1.set(false);
                lock_2.set(true);
                wench.set(0);
            }
            else  if (pressed == false) {
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                lock_1.set(true);
                lock_2.set(false);
            }
            
            if (rightStick.getRawButton(4)) {
                System.out.println(winchEncoder.get());
            }
            
/*intake-m*/if (intake) {
                intakeMotor.set(1);}
            else if (intake == false) {
                intakeMotor.set(0);}
/*intake-down*/
            if (intakeDown) {
                intake_1.set(true);
                intake_2.set(false);
            }
            else {
                intake_1.set(false);
                intake_2.set(true);
            }
/*shifters*/if (shifted) {
                shift_1.set(true);
                shift_2.set(false);}
            else {
                shift_1.set(false);
                shift_2.set(true);}

            
            Timer.delay(.01);
            
        }
    
    }
    public void test() {
    
    }
    
}
