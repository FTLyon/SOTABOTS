package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
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
    //DigitalOutput mode_1            = new DigitalOutput(1); **MUST re-wire these to 
//    DigitalOutput mode_2            = new DigitalOutput(2);   avoid conflict with current
//    DigitalOutput mode_3            = new DigitalOutput(3);   wiring.
//    DigitalOutput mode_groovy       = new DigitalOutput(4);
//    DigitalOutput mode_5            = new DigitalOutput(5);//
//    DigitalOutput mode_6            = new DigitalOutput(6);
    AnalogChannel accel_x           = new AnalogChannel(1);   //wiring of these occurs directly
    AnalogChannel accel_y           = new AnalogChannel(2);   //on CRIO analog module
    AnalogChannel gyro_x            = new AnalogChannel(3);
    Timer      time_1               = new Timer();
    
    boolean    intakeDown           = true;
    boolean    intake               = false;
    boolean    winchDown            = false;
    boolean    shifted              = false;
    boolean    locked               = false;
    boolean    shoot_1              = false;
    boolean    shoot_2              = false;
    boolean    first                = false;
    boolean    shoot_3              = false;
    boolean    unwind               = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
    double     fiveRev              = -10000;
    
    double     index                = 0;
    
    double     dist_1               = 0;
    
   
    public void autonomous() {
        //mode_1.set(true);
    }

    public void operatorControl() {
        compressor.start();
        winchEncoder.start();
        //mode_1.set(false);
        while (isOperatorControl() && isEnabled()) {
            //Network.NetIn();
            //System.out.print(accel_x.getAverageVoltage());
            //System.out.println(accel_y.getAverageVoltage());

            
                      
            if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) > 0.15)   {        
                drive.arcadeDrive(leftStick);}
            else if (Math.abs(leftStick.getAxis(Joystick.AxisType.kY)) > 0.15) {
                drive.arcadeDrive(leftStick);}
            else{
                drive.arcadeDrive(0,0);}
            
            wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                                   
/*left*/    if (leftStick.getRawButton(6)) {
                shifted = true;}
            else if (leftStick.getRawButton(7)) {
                shifted = false;}
            
            if (leftStick.getRawButton(3)) {
                intakeDown = true;
            }
            else if (leftStick.getRawButton(2)) {
                intakeDown = false;
            }
            
            if (leftStick.getRawButton(4)) {
                System.out.println(dist_1);
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
                dist_1 = 0;
            }
            else {
                lock_1.set(false);
                lock_2.set(true);
            }
            
///*groovy*/  if (rightStick.getRawButton(4)) { //button 4
//               // mode_1.set(false);
//                mode_2.set(false);
//                mode_3.set(false);
//                mode_groovy.set(true);
//            }
//            
///*mode2*/   if (rightStick.getRawButton(5)) { //button 5
//                //mode_1.set(false);
//                mode_2.set(true);
//                mode_3.set(false);
//                mode_groovy.set(false);
//            }
//
///*mode3*/   if (rightStick.getRawButton(9)) { //button 9
//               // mode_1.set(false);
//                mode_2.set(false);
//                mode_3.set(true);
//                mode_groovy.set(false);
//            }
                        
////////////////////////////////////////////////////////////////////////////////
            
/*winch*/   if (winchDown == true && lim_switch.get() == true && rightStick.getRawButton(0) == false && rightStick.getRawButton(3)) {
                wench.set(-.4);
                locked = false;}
            else if (lim_switch.get() == false && rightStick.getRawButton(0) == false && winchDown == true) {
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                dist_1 = 0;
                //winchDown = false;
                locked = true;}
            else if (lim_switch.get() == false && rightStick.getRawButton(3)) {
                locked = true;
                dist_1 = 0;
            }
            
            
            
/*lock*/    if (locked) {
                lock_1.set(false);
                lock_2.set(true);
                time_1.reset();
                time_1.start();
                System.out.println(time_1.get());
                unwind = true;
}
            else if (locked == false) {
                //System.out.println(winchEncoder.get());
                lock_1.set(true);
                lock_2.set(false);
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

/*winch prep for shoot*/
            if (unwind && dist_1 > fiveRev && time_1.get() > 1) {
                wench.set(.5);
                if (first = false) {
                    dist_1 = 0;
                    first = true;
                }
                else {
                    dist_1 += winchEncoder.get();
                }

            }
            else {
                unwind = false;
                winchEncoder.reset();
            }
            Timer.delay(.01);
            
        }
    
    }
    public void test() {
    
    }
    
    
}
