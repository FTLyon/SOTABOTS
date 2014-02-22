package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.io.BufferedReader;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;


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
    Encoder    winchEncoder         = new Encoder(6,7);
    Encoder    drive_1              = new Encoder(2,3);
    Encoder    drive_2              = new Encoder(4,5);
    DigitalOutput mode_1            = new DigitalOutput(8);
    DigitalOutput mode_2            = new DigitalOutput(9);   
    DigitalOutput mode_3            = new DigitalOutput(10);  
    DigitalOutput mode_4            = new DigitalOutput(11);
    DigitalOutput mode_5            = new DigitalOutput(12);
    SmartDashboard dash             = new SmartDashboard();
    Timer         time_1            = new Timer();
    
    DigitalOutput[] modes           = new DigitalOutput[] {mode_1,mode_2,mode_3, mode_4, mode_5};
    
    boolean    intakeDown           = true;
    int        intake               = 0;
    boolean    shifted              = false;
    boolean    shoot_1              = false;
    boolean    shoot_2              = false;
    boolean    shoot_3              = false;
    boolean    pressed              = false;
    boolean    winding              = false;
    boolean    killme               = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
    int        modeIndex            = 0;
    
    int        cycle                = 0;
    
    double[]   coordinates          = null;
    String[]   vision_coord         = null;

        
    public void autonomous() {
        compressor.start();
        winchEncoder.start();
        wench.set(0);
        lock_1.set(false);
        lock_2.set(true);
        drive.setSafetyEnabled(false);
        time_1.start();
        while (time_1.get() < 10) {
            SmartDashboard.putString("NET 1", (Network.NetIn()[0] + " " + Network.NetIn()[1]));
        }
            
    }

    public void operatorControl() {
        compressor.start();
        winchEncoder.start();
        wench.set(0);
        

        while (isOperatorControl() && isEnabled()) {
            SmartDashboard.putNumber("WINCH", winchEncoder.get());
            SmartDashboard.putBoolean("LOCK", pressed);
            SmartDashboard.putNumber("LED MODE", modeIndex);
            
            if (modeIndex == 0) {
                modes[4].set(false);
                modes[0].set(true);
                modes[1].set(false);
                modes[2].set(false);
                modes[3].set(false);}
            else if (modeIndex == 1) {
                modes[4].set(false);
                modes[0].set(false);
                modes[1].set(true);
                modes[2].set(false);
                modes[3].set(false);
            }
            else if (modeIndex == 2) {
                modes[4].set(false);
                modes[0].set(false);
                modes[1].set(false);
                modes[2].set(true);
                modes[3].set(false);
            }
            else if (modeIndex ==3) {
                modes[4].set(false);
                modes[0].set(false);
                modes[1].set(false);
                modes[2].set(false);
                modes[3].set(true);
            }
            else if (modeIndex == 4) {
                modes[4].set(true);
                modes[0].set(false);
                modes[1].set(false);
                modes[2].set(false);
                modes[3].set(false);
            }
            else if (modeIndex > 4) {
                modeIndex = 0;
            }

            
            if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) > 0.15)   {        
                drive.arcadeDrive(leftStick);}
            else if (Math.abs(leftStick.getAxis(Joystick.AxisType.kY)) > 0.15) {
                drive.arcadeDrive(leftStick);}
            else{
                drive.arcadeDrive(0,0);}
            
                                   
/*left*/    if (leftStick.getRawButton(3)) {
                shifted = true;}
            else if (leftStick.getRawButton(4)) {
                shifted = false;}
            
            
            else if (leftStick.getRawButton(5)) {
                intakeDown = false;
            }
            else if (leftStick.getRawButton(6)){
                intakeDown = true;
            }
            
            if (leftStick.getRawButton(12)) {
                System.out.println(winchEncoder.get());
            }
            
            if (leftStick.getTrigger()) {
                modeIndex ++;
            }
            
            if(leftStick.getRawButton(7)) {
                modeIndex = 1;
            }
            if (leftStick.getRawButton(8)) {
                modeIndex = 2;
            }
            
/*auto vision*/            
            if (leftStick.getRawButton(9) && leftStick.getRawButton(10)) {
                //target code here, pull from Vision class
            }
            
/*right*/   if (rightStick.getRawButton(3)) {
                intake = 1;
            }
            else if (rightStick.getRawButton(4)) {
                intake = 2;
            } else {
                intake = 0;
            }

            if (rightStick.getRawButton(2) && lim_switch.get() == true && pressed == false) {
                wench.set(-.8);
                winchEncoder.reset();
            }
            else if (lim_switch.get() == false && rightStick.getRawButton(2)) {
                //winchEncoder.reset();
                winding = true;
                lock_1.set(false);
                lock_2.set(true);
            }
            else if (lim_switch.get() == false && winchEncoder.get() < 550 && winding) { //put back to 550 for competition bot!
                pressed = true;
                lock_1.set(false);
                lock_2.set(true);
                wench.set(.8);
            }
            else if (rightStick.getTrigger()) {
                pressed = false;
                lock_1.set(true);
                lock_2.set(false);
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
            }
            else if (pressed && winchEncoder.get() >= 550) { //put back to 550 for competition bot!
                lock_1.set(false);
                lock_2.set(true);
                winding = false;
                wench.stopMotor();
            }
            else  if (pressed == false) {
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                lock_1.set(true);
                lock_2.set(false);
            }
            
            if (rightStick.getRawButton(5)) {
                System.out.println(winchEncoder.get());
            }
            
/*intake-m*/
            SetIntakeMotor(intake);
            
/*intake-down*/
            if (intakeDown) {
                intake_1.set(false); //SWITCH FOR COMPETITION BOT!
                intake_2.set(true);
            }
            else {
                intake_1.set(true);
                intake_2.set(false);
            }
/*shifters*/if (shifted) {
                shift_1.set(true);
                shift_2.set(false);
            }
            else {
                shift_1.set(false);
                shift_2.set(true);}

            
            Timer.delay(.01);
            
        }
    }
    
    private void SetIntakeMotor(int status)
    {
        /* 0 = stop */
        /* 1 = forward */
        /* 2 = backward */
        
        if(status == 0)
        {
            intakeMotor.stopMotor();
        } 
        
        else if(status == 1)
        {
            intakeMotor.set(1);
        } 
        
        else if(status == 2)
        {
            intakeMotor.set(-1);
        }
    }
    
    public void test() {
    
    }
    
}
