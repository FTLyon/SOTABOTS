package edu.wpi.first.wpilibj.templates;
//KIRK FOREMAN IS ONLY WORTH SOMETHING AS A HUMAN SHIELD/BULLET SPONGE

import edu.wpi.first.wpilibj.*;


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
    DigitalOutput mode_5            = new DigitalOutput(12);
    
    DigitalOutput[] modes           = new DigitalOutput[] {mode_1,mode_2,mode_3, mode_4, mode_5};
    
    boolean    pressed              = false;
    boolean    leftRight            = false;
    
    double     driveLeft            = 0;
    double     driveRight           = 0;
    
    int        modeIndex            = 0;
    int        modeArm              = 0;
    int        modeShift            = 0;
    int        modeFire             = 0;
    int        intake               = 0;
    
    String[]   vision_coord         = null;
    double[]   coordinates          = null;
    double[]   autoTarget_1         = null;
    double[]   autoTarget_2         = null;


    public void autonomous() {
        compressor.start();
        Fire(0);
        
        modes[0].set(true); //yellow mode (1) for auto
        
        drive_1.start(); //enable and reset encoders for auto mode or something
        drive_2.start();
        winchEncoder.start();
        drive_1.reset();
        drive_2.reset();
        winchEncoder.reset();
        
        vision_coord = Network.NetIn();             //call and parse coordinates for targeting
        autoTarget_1 = Vision.average(Double.parseDouble(vision_coord[0]), Double.parseDouble(vision_coord[1]));
        autoTarget_2 = Vision.average(Double.parseDouble(vision_coord[2]), Double.parseDouble(vision_coord[3]));
            
        leftRight = Vision.leftRight(autoTarget_1,autoTarget_2); //determine whether the target is on the L/R
        System.out.println(leftRight);
            
        AutoTarget(leftRight);              //runs targeting method
        Timer.delay(1);                     //wait 1s
        Fire(1);                            //fires
        drive_1.reset();                    //resets drive encoder
        while (drive_1.get() < 1000) {
          drive.arcadeDrive(.5,0);          //drive forward for 1000 encoder counts
            }
        drive.arcadeDrive(0,0);             //stop
        Fire(0);                            //unlatch
        }

    public void operatorControl() {
        compressor.start();
        winchEncoder.start();
        modes[0].set(false);

        while (isOperatorControl() && isEnabled()) {
            vision_coord = Network.NetIn();
            coordinates = Vision.average(Double.parseDouble(vision_coord[0]), Double.parseDouble(vision_coord[1]));
            
            
            if (Math.abs(leftStick.getAxis(Joystick.AxisType.kX)) > 0.15)   {   //drive
                drive.arcadeDrive(leftStick);}
            else if (Math.abs(leftStick.getAxis(Joystick.AxisType.kY)) > 0.15) {
                drive.arcadeDrive(leftStick);}
            else{
                drive.arcadeDrive(0,0);}
            
                                   
/*left*/    if (leftStick.getRawButton(6)) {                                    //supershifters
                modeShift = 1;}
            else if (leftStick.getRawButton(7)) {
                modeShift = 0;}
            
            
            else if (leftStick.getRawButton(2)) {                               //intake arm
                modeArm = 1;
            }
            else if (leftStick.getRawButton(3)){
                modeArm = 0;
            }
            
            if (leftStick.getRawButton(4)) {                                    //printing coordinates and encoder val
                System.out.println(winchEncoder.get() + " " + coordinates[0] + " " + coordinates[1]);
            }
            
            if (leftStick.getTrigger()) {                                       //cycling LED modes
                modeIndex++;
            }
            
            
/*right*/   if (rightStick.getRawButton(7)) {                                   //intake
                intake = 1;
            }
            else if (rightStick.getRawButton(6)) {
                intake = 2;
            } else {
                intake = 0;
            }

            if (rightStick.getRawButton(3) && lim_switch.get() == true) {       //winch
                wench.set(-.7);
            }
            else if (lim_switch.get() == false && rightStick.getRawButton(3)) {
                winchEncoder.reset();
                modeFire = 0;
            }
            else if (lim_switch.get() == false && winchEncoder.get() < 550) {
                pressed = true;
                modeFire = 0;
                wench.set(.7);
            }
            else if (rightStick.getTrigger()) {
                pressed = false;
                modeFire = 1;
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
            }
            else if (pressed && winchEncoder.get() > 550) {
                modeFire = 0;
                wench.set(0);
            }
            else  if (pressed == false) {
                wench.set(rightStick.getAxis(Joystick.AxisType.kY));
                modeFire = 1;
            }
            
            if (rightStick.getRawButton(4)) {                                   //print winch encoder
                System.out.println(winchEncoder.get());
            }
            
/*---------------------------------------------------------------------------*/

            SetIntakeMotor(intake);
            SetIntakeArm(modeArm);
            Shift(modeShift);
            LedMode(modeIndex);
            Fire(modeFire);
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
    private void AutoTarget(boolean leftRight) {
        /* false = left
         * true  = right
         */
        if (leftRight == false) { //target lit up on left
            while (drive_1.get() < 900) {
                drive.arcadeDrive(0, -.3);
            }
            drive.arcadeDrive(0,0);
        }
        else if (leftRight = true) { //target lit up on right
            while (drive_2.get() < 900) {
                drive.arcadeDrive(0, .3);
            }
        }
    }
    private void Fire(int state) {
        /* 0 = unlatch 
           1 = latch   */
        if (state == 0) {
            lock_1.set(false);
            lock_2.set(true);
        }
        else if (state == 1) {
            lock_1.set(true);
            lock_2.set(false);
        }        
    }
    private void LedMode(int index) {
        /* 0 = yellow
         * 1 = blue
         * 2 = red
         * 3 = 'murica
         * 4 = groovy
         */
        if (index > 4) {        
            modeIndex = 0;
        }
        for (int i = 0; i < modes.length - 1; i ++) { 
            if (i == index)
                modes[i].set(true);
            else
                modes[i].set(false);
             }
    }
    private void Shift(int state) {
        /* 0 = not shifted
         * 1 = shifted
         */
        if (state == 0) {
            shift_1.set(false);
            shift_2.set(true);
        }
        else if (state == 1) {
            shift_1.set(true);
            shift_2.set(false);
        }
    }
    private void SetIntakeArm(int state) {
        /* 0 = down
         * 1 = up
         */
        if (state == 0) {
            intake_1.set(true);
            intake_2.set(false);
        }
        else if (state == 1) {
            intake_1.set(false);
            intake_2.set(true);
        }
    }
    
    public void test() {
    
    }
    
}
