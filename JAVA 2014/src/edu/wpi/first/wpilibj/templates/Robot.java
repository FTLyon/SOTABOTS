package edu.wpi.first.wpilibj.templates;

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
    
    
    DigitalOutput[] modes           = new DigitalOutput[] {mode_1,mode_2,mode_3, mode_4, mode_5};
    
    boolean    intakeDown           = true;
    int        intake               = 0;
    boolean    shifted              = false;
    boolean    shoot_1              = false;
    boolean    shoot_2              = false;
    boolean    shoot_3              = false;
    boolean    pressed              = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
    int        modeIndex            = 0;
    
    double[]   coordinates          = null;
    String[]   vision_coord         = null;

    public void autonomous() {
//        mode_1.set(true);
//        compressor.start();
//        Fire(0);
//        
//        modes[0].set(true); //yellow mode (1) for auto
//        
//        drive_1.start(); //enable and reset encoders for auto mode or something
//        drive_2.start();
//        winchEncoder.start();
//        drive_1.reset();
//        drive_2.reset();
//        winchEncoder.reset();
//        
//        vision_coord = Network.NetIn();             //call and parse coordinates for targeting
//        autoTarget_1 = Vision.average(Double.parseDouble(vision_coord[0]), Double.parseDouble(vision_coord[1]));
//        autoTarget_2 = Vision.average(Double.parseDouble(vision_coord[2]), Double.parseDouble(vision_coord[3]));
//            
//        leftRight = Vision.leftRight(autoTarget_1,autoTarget_2); //determine whether the target is on the L/R
//        System.out.println(leftRight);
//            
//        AutoTarget(leftRight);              //runs targeting method
//        Timer.delay(1);                     //wait 1s
//        Fire(1);                            //fires
//        drive_1.reset();                    //resets drive encoder
//        while (drive_1.get() < 1000) {
//          drive.arcadeDrive(.5,0);          //drive forward for 1000 encoder counts
//            }
//        drive.arcadeDrive(0,0);             //stop
//        Fire(0);                            //unlatch
    }

    public void operatorControl() {
        compressor.start();
        winchEncoder.start();

        while (isOperatorControl() && isEnabled()) {
            try {
            vision_coord = Network.NetIn();
            coordinates  = Vision.average(Double.parseDouble(vision_coord[0]), Double.parseDouble(vision_coord[1]));
            System.out.println(coordinates[0] + " " + coordinates[1]);}
            catch (Exception e) {
                System.out.println("Pi connection issue or something.");
            }
            
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
            
                                   
/*left*/    if (leftStick.getRawButton(6)) {
                shifted = true;}
            else if (leftStick.getRawButton(7)) {
                shifted = false;}
            
            
            else if (leftStick.getRawButton(2)) {
                intakeDown = false;
            }
            else if (leftStick.getRawButton(3)){
                intakeDown = true;
            }
            
            if (leftStick.getRawButton(4)) {
                System.out.println(winchEncoder.get());
            }
            
            if (leftStick.getTrigger()) {
                modeIndex ++;
            }
            
            if(leftStick.getRawButton(8)) {
                modeIndex = 1;
            }
            if (leftStick.getRawButton(9)) {
                modeIndex = 2;
            }
            
/*auto vision*/            
            if (leftStick.getRawButton(8) && leftStick.getRawButton(9)) {
                //target code here, pull from Vision class
            }
            
/*right*/   if (rightStick.getRawButton(7)) {
                intake = 1;
            }
            else if (rightStick.getRawButton(6)) {
                intake = 2;
            } else {
                intake = 0;
            }

            if (rightStick.getRawButton(3) && lim_switch.get() == true) {
                wench.set(-.7);
            }
            else if (lim_switch.get() == false && rightStick.getRawButton(3)) {
                winchEncoder.reset();
                
                lock_1.set(false);
                lock_2.set(true);
            }
            else if (lim_switch.get() == false && winchEncoder.get() < 550) {
                pressed = true;
                lock_1.set(false);
                lock_2.set(true);
                wench.set(.7);
            }
            else if (rightStick.getTrigger()) {
                pressed = false;
                lock_1.set(true);
                lock_2.set(false);
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
            }
            else if (pressed && winchEncoder.get() >= 550) {
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
            
/*intake-m*/
            SetIntakeMotor(intake);
            
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
    
    private void SetIntakeMotor(int status)
    {
        /* 0 = stop */
        /* 1 = forward */
        /* 2 = backward */
        
        if(status == 0)
        {
            intakeMotor.stopMotor();
        } else if(status == 1)
        {
            intakeMotor.set(1);
        } else if(status == 2)
        {
            intakeMotor.set(-1);
        }
    }
    public void test() {
    
    }
}
