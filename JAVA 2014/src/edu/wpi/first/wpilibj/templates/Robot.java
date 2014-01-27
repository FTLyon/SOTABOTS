package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
    RobotDrive drive;
    AnalogChannel ard_X; //analog inputs from arduino
    AnalogChannel ard_Y;
    Talon winch;
    Talon intake;
    DigitalInput limSwitch;
    DoubleSolenoid kicker;
    Encoder     leftDrive;
    Encoder     rightDrive;
    
    
    static double X, Y; //translation (strings to doubles) from Pi (from vision class)
    double[] xy; //Pi target coordinate values after moving average filter
       
    public static double motor_x; //motor x-value
    public static double motor_y; //motor y-value
    
    public static double lz_X; //localized x-coordinate
    public static double lz_Y; //localized y-coordinate
    public static double t;    //timer value used for acceleration -> position integration 
    Timer time;
    
    public void robotInit() {
        drive       = new RobotDrive(Map.leftDriveMotor, Map.rightDriveMotor);
        ard_X       = new AnalogChannel(Map.arduino_X);
        ard_Y       = new AnalogChannel(Map.arduino_Y);
        winch       = new Talon(Map.winchMotor);
        intake      = new Talon(Map.intakeMotor);
        limSwitch   = new DigitalInput(Map.limSwitchPort);
        kicker      = new DoubleSolenoid(Map.kicker1, Map.kicker2);
        leftDrive   = new Encoder(Map.leftEncoder_a, Map.leftEncoder_b);
        rightDrive  = new Encoder(Map.rightEncoder_a, Map.rightEncoder_b);
        
        xy      = Vision.average(X, Y);
        lz_X    = 0;
        lz_Y    = 0;
        
        Network.NetIn();
    }
    
    public void autonomousPeriodic() {
        
    }

   
    public void teleopPeriodic() {
        
        Network.NetIn();
        xy = Accelerometer.average(ard_X.getValue(), ard_Y.getValue());
        Accelerometer.localize(xy[0], xy[1]);
        Vision.average(X, Y);
        
        //timer start/stop operations for localization calculations
        if (motor_x != 0 || motor_y != 0) 
            time.start();
        else if (Math.abs(motor_x) < .05 && Math.abs(motor_y) < .05) 
            time.stop();
            time.reset();
            t = 0;
        t = time.get();
        
        
        
        
        System.out.println("Localized coordinates:  " + lz_X + " " + lz_Y);
        System.out.print("Vision coordinates:  " + X + " " + Y);
        
        
    }
    
    public void testPeriodic() {
        //method used for testing of basic code, prototyping, etc.
    }
}