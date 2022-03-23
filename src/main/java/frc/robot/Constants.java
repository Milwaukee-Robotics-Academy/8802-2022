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
        public static final int kLeftMotorFrontPort = 1; //can id
        public static final int kLeftMotorRearPort = 2; //can id
        public static final int kRightMotorFrontPort = 3; //can id
        public static final int kRightMotorRearPort = 4; //can id
        public static final int kLeftEncoder1 = 0;
        public static final int kLeftEncoder2 = 1;
        public static final int kRightEncoder1 = 2;
        public static final int kRightEncoder2 = 3;
        
    }
    public static final class IntakeConstants {
        public static final int kIntakeMotor = 6; //pwm
    }
    public static final class StorageConstants {
        public static final int kStorageLeft = 4; //pwm
        public static final int kStorageRight = 7; //pwm

    }
    public static final class ShooterConstants{
        public static final int kFrontFollowerMotorPort = 0; //
        public static final int kFrontMotorPort = 1; //
        public static final int kBackFollowerMotorPort = 2; //
        public static final int kBackMotorPort = 3; //
    }
    public static final class ClimberConstants{
        public static final int kClimberMotorLeft = 5; //can
        public static final int kClimberMotorRight = 6; //can
    }
    public static final class BlowerConstants{

        public static final int kRightMotorPort = 8; //can id 8
        public static final int kLeftMotorPort = 7; //can id 7

    }
}
