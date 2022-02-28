/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;
import io.github.oblarg.oblog.annotations.Log;

public class Drive extends SubsystemBase implements Loggable {
  private final WPI_TalonFX m_leftMotor = new WPI_TalonFX(DriveConstants.kLeftMotorFrontPort);
  private final WPI_TalonFX m_leftFollowerMotor = new WPI_TalonFX(DriveConstants.kLeftMotorRearPort);
  private final WPI_TalonFX m_rightMotor = new WPI_TalonFX(DriveConstants.kRightMotorFrontPort);
  private final WPI_TalonFX m_rightFollowerMotor = new WPI_TalonFX(DriveConstants.kRightMotorRearPort);
  SendableChooser<Boolean> m_preventTilt = new SendableChooser<>();
  private final SlewRateLimiter m_accLimiter = new SlewRateLimiter(.5);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(.5);
  
  @Log.Gyro
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  @Log.DifferentialDrive
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

 /**
   * Creates a new drive.
   */
  public Drive() {

    m_rightMotor.configFactoryDefault();
    m_rightFollowerMotor.configFactoryDefault();
    m_leftMotor.configFactoryDefault();
    m_leftFollowerMotor.configFactoryDefault();
    
    m_leftFollowerMotor.follow(m_leftMotor);
    m_rightFollowerMotor.follow(m_rightMotor);

    m_rightMotor.setInverted(TalonFXInvertType.CounterClockwise);
    m_leftMotor.setInverted(TalonFXInvertType.Clockwise);

    m_rightFollowerMotor.setInverted(InvertType.FollowMaster);
    m_leftFollowerMotor.setInverted(InvertType.FollowMaster);

    m_preventTilt.setDefaultOption("Tilt Assist OFF", false);
    m_preventTilt.addOption("Tilt Assist ON", true);
    
    SmartDashboard.putData(m_preventTilt);
  
  }

  public void drive(double rightThrottle, double leftThrottle, double rotation) {
    double rollAngleDegrees     = m_gyro.getRoll();

    if (m_preventTilt.getSelected() && Math.abs(rollAngleDegrees) > 10) {
      //alter based on tilt
      m_robotDrive.arcadeDrive(m_accLimiter.calculate(rightThrottle - leftThrottle), -rotation+Math.sin( rollAngleDegrees * (Math.PI / 180.0)) * -1);
    } else {}
     m_robotDrive.arcadeDrive(m_accLimiter.calculate(rightThrottle - leftThrottle), m_rotLimiter.calculate(-rotation));
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
    m_leftMotor.set(left);
    m_rightMotor.set(right);
  }
  
  public void stopDrive(){
    this.tank(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
@Log(name = "Left Encoder")
  public Double getLeftEncoderPosition(){
    return m_leftMotor.getSelectedSensorPosition();
  }

  @Log(name = "Right Encoder")
  public Double getRightEncoderPosition(){
    return m_rightMotor.getSelectedSensorPosition();
  }
}