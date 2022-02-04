/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The constants for the different robot subsystems
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kLeftMotorFrontPort = 0; //can id
        public static final int kLeftMotorRearPort = 1; //can id
        public static final int kRightMotorFrontPort = 2; //can id
        public static final int kRightMotorRearPort = 3; //can id
        public static final int kLeftEncoder1 = 0;
        public static final int kLeftEncoder2 = 1;
        public static final int kRightEncoder1 = 2;
        public static final int kRightEncoder2 = 3;
        public static final double kP = 6e-5;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kIz = 0;
        public static final double kFF = 0.000015;  
        public static final double kMaxSpeed = 3.0; // meters per second
        public static final double kMaxAngularSpeed = 2 * Math.PI; // one rotation per second
        public static final double kTrackWidth = 0.5588; // meters
        public static final double kWheelRadius = 0.0762; // meters
        public static final int kEncoderResolution = 20; //CIMcoder has 20 CPR this may need to be adjusted * 10.71 for gearbox
        public static final double kSlewSpeedLimit = .3;// Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
        public static final double kSlewRotLimit = .3;  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
    }
    public static final class IntakeConstants {
        public static final int kIntakeMotor = 6; //pwm
        public static final int kIntakeSolenoid1 = 3;
        public static final int kIntakeSolenooid2 = 0;
    }
    public static final class BlowerConstants {
        public static final int kMain = 4; //can
        public static final int kFollower = 7; //can
        public static final double kP = 6e-5;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kIz = 0;
        public static final double kFF = 0.000015;
        public static final double kMaxOutput = 1;
        public static final double kMinOutput = 0;
        public static final double kMaxRPM = 5700;
        
                
    }
    public static final class ShooterConstants{
        public static final int kFrontFollowerMotorPort = 0; //
        public static final int kFrontMotorPort = 1; //
        public static final int kBackFollowerMotorPort = 2; //
        public static final int kBackMotorPort = 3; //
        public static final int kShooterSolenoid1 = 4;
        public static final int kShooterSolenoid2 = 1;
    }
    public static final class ClimberConstants{
        public static final int kClimberMotorLeft = 5; //pwm
        public static final int kClimberMotorRight = 8; //pwm
        public static final int kClimberSolenoid1 = 6;
        public static final int kClimberSolenoid2 = 7;
    }
}
