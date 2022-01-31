/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Drive extends SubsystemBase implements Loggable {
  private final Victor m_LeftMotor = new Victor(DriveConstants.kLeftMotorFrontPort);
  private final Victor m_LeftFollowerMotor = new Victor(DriveConstants.kLeftMotorRearPort);
  private final Victor m_RightMotor = new Victor(DriveConstants.kRightMotorFrontPort);
  private final Victor m_RightFollowerMotor = new Victor(DriveConstants.kRightMotorRearPort);
  private final MotorControllerGroup m_LeftMotors = new MotorControllerGroup(m_LeftMotor, m_LeftFollowerMotor);
  private final MotorControllerGroup m_RightMotors = new MotorControllerGroup(m_RightMotor, m_RightFollowerMotor);
  private final Encoder m_LeftEncoder = new Encoder(DriveConstants.kLeftEncoder1,DriveConstants.kLeftEncoder2);
  private final Encoder m_RightEncoder = new Encoder(DriveConstants.kRightEncoder1,DriveConstants.kRightEncoder2);

  @Log.Gyro
  private final AHRS m_gyroscope = new AHRS(SPI.Port.kMXP);

  @Log.DifferentialDrive
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_LeftMotors, m_RightMotors);

 /**
   * Creates a new drive.
   */
  public Drive() {
    m_LeftMotors.setInverted(true);
  }

  public void drive(double rightThrottle, double leftThrottle, double rotation) {
     m_robotDrive.arcadeDrive(this.deadband(rightThrottle - leftThrottle), this.deadband(-rotation));
    }
    public double deadband(double value){
      //Upper Deadband//
      if (value >= +0.2)
        return value; 

      //Lower Deadband//
      if (value <= -0.2)
        return value;

      //Outside Deadband//
      return 0;
    }

  public void driveTank(double d, double e){
    m_robotDrive.tankDrive(d, e);
  }

  public void vision() {

  }

  @Config
  public void tank(double left, double right){
    m_LeftMotor.set(left);
    m_RightMotor.set(right);
  }
  
  public void stopDrive(){
    this.tank(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
@Log(name = "Left Encoder")
  public int getLeftEncoderPosition(){
    return m_LeftEncoder.get();
  }

  @Log(name = "Right Encoder")
  public int getRightEncoderPosition(){
    return m_RightEncoder.get();
  }
}